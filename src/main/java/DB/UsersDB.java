package DB;


import Users.User;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.*;


public class UsersDB implements IUsersDB {

    private static final UsersDB instance = new UsersDB();

    Morphia morphia;
    MongoClient m;
    Datastore datastore;

    private UsersDB() {
        morphia = new Morphia();
        morphia.mapPackage("Users");
        m = new MongoClient("localhost", 27017);
        datastore = morphia.createDatastore(m, "systemDatabase");
    }

    public static UsersDB getInstance() {
        return instance;
    }

    @Override
    public void changeDataStore(String newDataStore) {
        datastore = morphia.createDatastore(m, newDataStore);
    }

    @Override
    public boolean isExistUser(String username) {
        final Query<User> query = datastore.createQuery(User.class).filter("username =", username);
        List<User> users = query.asList();
        if (users.size() == 0) return false;
        return true;
    }

    @Override
    public User getUser(String username) {
        final Query<User> query = datastore.createQuery(User.class).filter("username =", username);
        List<User> users = query.asList();
        if (users.size() == 0) return null;
        return users.get(0);
    }

    @Override
    public void register(User u) {
        u.setLoggedIn(true);
        datastore.save(u);
    }

    @Override
    public User login(String username) {
        User u = getUser(username);
        u.setLoggedIn(true);
        datastore.save(u);
        return u;
    }

    @Override
    public void logout(String username) {
        User u = getUser(username);
        u.setLoggedIn(false);
        datastore.save(u);
    }

    @Override
    public boolean isUsernameAndPasswordMatch(String username, String password) {
        User u = getUser(username);
        return (u.getUsername().equals(username) && u.getPassword().equals(password));
    }

    @Override
    public boolean isLoggedIn(String username) {
        User u = getUser(username);
        return u.isLoggedIn();
    }

    @Override
    public void setEmail(String username, String email) {
        User u = getUser(username);
        u.setEmail(email);
        datastore.save(u);
    }

    @Override
    public void setPassword(String username, String password) {
        User u = getUser(username);
        u.setPassword(password);
        datastore.save(u);
    }

    @Override
    public void setUsername(String oldUsername, String newUsername) {
        User u = getUser(oldUsername);
        u.setUsername(newUsername);
        datastore.save(u);
    }

    @Override
    public void moveUserToLeague(String username, int newLeague) {
        User u = getUser(username);
        u.setLeague(newLeague);
        datastore.save(u);
    }

    @Override
    public ArrayList<Integer> allLeagues() {
        final Query<User> query = datastore.createQuery(User.class);
        List<User> users = query.asList();
        ArrayList<Integer> ret = new ArrayList<>();
        for (User u : users) {
            if (!ret.contains(u.getLeague())) ret.add(u.getLeague());
        }
        return ret;
    }

    @Override
    public ArrayList<User> allUsers() {
        final Query<User> query = datastore.createQuery(User.class);
        ArrayList<User> users = new ArrayList<>(query.asList());
        return users;
    }

    @Override
    public void organizeLeagues() {

        int numOfUsersInLeague;
        int numOfLeagues = 0;
        ArrayList<User> allUsers = allUsers();
        ArrayList<Integer> allLeagues = allLeagues();
        Collections.sort(allLeagues);

        numOfUsersInLeague = allUsers.size() / numOfLeagues;
        int remainder = allUsers.size() % numOfLeagues;

        if (numOfUsersInLeague < 2) {
            //now I should check what league I should remain empty
            int numOfLeaguesToSkip = 0;
            while ((allUsers.size() / (numOfLeagues - numOfLeaguesToSkip)) < 2) numOfLeaguesToSkip++;

            numOfUsersInLeague = (allUsers.size() / (numOfLeagues - numOfLeaguesToSkip));
            remainder = (allUsers.size() % (numOfLeagues - numOfLeaguesToSkip));

            int from = 0;
            int to = numOfUsersInLeague;

            Iterator it = allLeagues.iterator();
            while (it.hasNext()) {

                int league = (Integer) it.next();
                if (numOfLeaguesToSkip > 0) {
                    numOfLeaguesToSkip--;
                } else {
                    ArrayList<User> temp = (ArrayList<User>) allUsers.subList(from, to);

                    from += numOfUsersInLeague;
                    if ((allUsers.size() - to) > remainder)
                        to += numOfUsersInLeague;

                    if (!it.hasNext()) {
                        temp.addAll(new ArrayList<>(allUsers.subList(from, allUsers.size())));
                    }
                    for (User u : temp) {
                        moveUserToLeague(u.getUsername(), league);
                    }
                }
            }
        } else {
            int from = 0;
            int to = numOfUsersInLeague;

            Iterator it = allLeagues.iterator();
            while (it.hasNext()) {
                int league = (Integer) it.next();
                ArrayList<User> temp = (ArrayList<User>) allUsers.subList(from, to);

                from += numOfUsersInLeague;
                if ((allUsers.size() - to) > remainder)
                    to += numOfUsersInLeague;

                if (!it.hasNext()) {
                    temp.addAll(new ArrayList<>(allUsers.subList(from, allUsers.size())));
                }
                for (User u : temp) {
                    moveUserToLeague(u.getUsername(), league);
                }
            }
        }

    }

    @Override
    public void deleteAllUsers() {
        final Query<User> query = datastore.createQuery(User.class);
        datastore.delete(query);
    }


    @Override
    public void setNumOfGames(String username, int num) {
        User u = getUser(username);
        u.setNumOfGames(num);
        datastore.save(u);
    }

    @Override
    public List<User> getTop20NumOfGames() {
        Query<User> query = datastore.createQuery(User.class).order("-numOfGames");
        List<User> ret = query.asList();
        if (ret.size() < 20) return ret;
        else {
            ret = ret.subList(0, 20);
            return ret;
        }
    }

    @Override
    public List<User> getTop20highestCashGained() {
        Query<User> query = datastore.createQuery(User.class).order("-highestCashGained");
        List<User> ret = query.asList();
        if (ret.size() < 20) return ret;
        else {
            ret = ret.subList(0, 20);
            return ret;
        }
    }

    @Override
    public List<User> getTop20GrossProfit() {
        Query<User> query = datastore.createQuery(User.class).order("-grossProfit");
        List<User> ret = query.asList();
        if (ret.size() < 20) return ret;
        else {
            ret = ret.subList(0, 20);
            return ret;
        }
    }

    @Override
    public double getUserAverageCashGain(String username)
    {
        User u = getUser(username);
        int totalCashGain = u.getTotalCashGain();
        int numOfGames = u.getNumOfGames();
        double ret =0;
        if(numOfGames != 0)
        {
            ret = (totalCashGain * 1.0) / (numOfGames * 1.0);
        }
        return ret ;
    }

    @Override
    public double getUserAverageGrossProfit(String username)
    {
        User u = getUser(username);
        int totalgrossProfit = u.getGrossProfit();
        int numOfGames = u.getNumOfGames();
        double ret = 0;
        if(numOfGames != 0)
        {
            ret = (totalgrossProfit * 1.0) / (numOfGames * 1.0);
        }
        return ret ;
    }

    @Override
    public void setTotalCashGain (String username , int totalCashGain)
    {
        User u = getUser(username);
        u.setTotalCashGain(totalCashGain);
        datastore.save(u);
    }

    @Override
    public void setGrossProfit (String username , int grossProfit)
    {
        User u = getUser(username);
        u.setGrossProfit(grossProfit);
        datastore.save(u);
    }

    @Override
    public void setHighestCashGained (String username , int highestCashGained)
    {
        User u = getUser(username);
        u.setHighestCashGained(highestCashGained);
        datastore.save(u);
    }

    @Override
    public int getTotalCashGain (String username )
    {
        User u = getUser(username);
        return u.getTotalCashGain();
    }

    @Override
    public int getGrossProfit (String username)
    {
        User u = getUser(username);
        return u.getGrossProfit();
    }

    @Override
    public int getHighestCashGained (String username)
    {
        User u = getUser(username);
        return u.getHighestCashGained();
    }





}






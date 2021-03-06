package DB;

import Users.User;

import java.util.ArrayList;
import java.util.List;

public interface IUsersDB {
    static UsersDB getInstance() {
        return UsersDB.getInstance();
    }

    boolean isExistUser(String username);

    User getUser(String username);

    void register(User u);

    User login(String username);

    void logout(String username);

    boolean isUsernameAndPasswordMatch(String username, String password);

    boolean isLoggedIn(String username);

    void setEmail(String username, String email);

    void setPassword(String username, String password);

    void setUsername(String oldUsername, String newUsername);

    void moveUserToLeague(String username, int newLeague);

    ArrayList<Integer> allLeagues();

    ArrayList<User> allUsers();

    void organizeLeagues();

    void deleteAllUsers();

    void changeDataStore(String newDataStore);

    List<User> getTop20NumOfGames();

    List<User> getTop20highestCashGained();

    List<User> getTop20GrossProfit();

    void setNumOfGames(String username, int num);

    double getUserAverageCashGain(String username);

    double getUserAverageGrossProfit(String username);

    void setTotalCashGain(String username, int totalCashGain);

    void setGrossProfit(String username, int grossProfit);

    void setHighestCashGained(String username, int highestCashGained);

    int getTotalCashGain(String username);

    int getGrossProfit(String username);

    int getHighestCashGained(String username);


}

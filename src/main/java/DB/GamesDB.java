package DB;

import Games.IGame;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by tamir on 05/06/2017.
 */
public class GamesDB implements IGamesDB{
    private static final GamesDB instance = new GamesDB();

    Morphia morphia;
    MongoClient m;
    Datastore datastore;

    private GamesDB() {
        morphia = new Morphia();
        morphia.mapPackage("Games");
        m = new MongoClient("localhost", 27017);
        datastore = morphia.createDatastore(m, "system");
    }

    public static GamesDB getInstance() {
        return instance;
    }

    @Override
    public IGame getGame(int id) {
        final Query<IGame> query = datastore.createQuery(IGame.class).filter("id =", id);
        List<IGame> users = query.asList();
        return users.get(0);
    }

    @Override
    public void save(IGame game) {
        datastore.save(game);
    }
}
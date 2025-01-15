package DataLayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class WatchListFinder {
    private static final String location = "/data/watchlist";
    private static WatchListFinder instance;
    public static WatchListFinder getInstance() {
        if (instance == null) {
            instance = new WatchListFinder();
        }
        return instance;
    }
    public static WatchListGateway findWatchList(String name) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(location + "/" + name + "_user.json")) {
            Type type = new TypeToken<UserGateway>() {
            }.getType();
            WatchListGateway loadedWatchList = gson.fromJson(reader, type);
            return loadedWatchList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

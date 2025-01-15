package DataLayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WatchListFinder {
    private static final String location = "data/watchlist";
    private static WatchListFinder instance;
    public static WatchListFinder getInstance() {
        if (instance == null) {
            instance = new WatchListFinder();
        }
        return instance;
    }
    public static WatchListGateway findWatchList(String name) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(location + "/" + name + "_watchlist.json")) {
            Type type = new TypeToken<WatchListGateway>() {}.getType();
            WatchListGateway loadedWatchList = gson.fromJson(reader, type);
            return loadedWatchList;
        } catch (FileNotFoundException e) {
            WatchListGateway newWatchList = new WatchListGateway(name);
            try (FileWriter writer = new FileWriter(location + "/" + name + "_watchlist.json")) {
                Gson gsonWriter = new Gson();
                gsonWriter.toJson(newWatchList, writer);
                return newWatchList;
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAllWatchListsName() {
        List<String> watchlists = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(location), "*_watchlist.json")) {
            for (Path entry : stream) {
                String fileName = entry.getFileName().toString();
                String userName = fileName.substring(0, fileName.indexOf("_watchlist.json"));
                watchlists.add(userName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return watchlists;
    }
}

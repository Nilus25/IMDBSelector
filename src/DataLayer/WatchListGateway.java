package DataLayer;

import Domain.Classes.Movie;
import Domain.Classes.Watchlist;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchListGateway {
    private String name;
    private List<Movie> watchList;
    private static final String location = "data/watchlist";
    public WatchListGateway(Watchlist watchList){
        this.name = watchList.getName();
        this.watchList = watchList.getWatchlist();
    }
    public WatchListGateway(String name){
        this.name = name;
        this.watchList = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public List<Movie> getWatchList() {
        return watchList;
    }
    public void save() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location + "/" + name + "_watchlist.json")) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            Files.delete(Path.of(location + "/" + name + "_watchlist.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

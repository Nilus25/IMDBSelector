package DataLayer;

import Domain.Movie;
import Domain.WatchList;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WatchListGateway {
    private String name;
    private List<Movie> watchList;
    private static final String location = "/data/watchlist";
    public WatchListGateway(WatchList watchList){
        this.name = watchList.getName();
        this.watchList = watchList.getWatchList();
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
}

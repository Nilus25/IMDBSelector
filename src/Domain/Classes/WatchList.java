package Domain.Classes;

import DataLayer.WatchListGateway;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchList {
    private String name;
    private List<Movie> watchList;
    public WatchList(String name, String watchList){
        this.name = name;
        try {
            this.watchList = CSVParser.parseCSV(watchList);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
    public WatchList(WatchListGateway watchList){
        this.name = watchList.getName();
        this.watchList = watchList.getWatchList();
    }
    public String getName() {
        return name;
    }
    public List<Movie> getWatchList() {
        return watchList;
    }
    public Movie getMovieByTitle(String name) {
        for (Movie movie : watchList) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        for (Movie movie : watchList) {
            tags.add(movie.getIMDBTag());
        }
        return tags;
    }

    public Movie getMovieByTag(String tag) {
        for (Movie movie : watchList) {
            if (movie.getIMDBTag().equals(tag)) {
                return movie;
            }
        }
        return null;
    }
}

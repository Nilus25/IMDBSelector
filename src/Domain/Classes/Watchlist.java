package Domain.Classes;

import DataLayer.WatchListGateway;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Watchlist {
    private String name;
    private List<Movie> watchlist;
    public Watchlist(String name, String watchList){
        this.name = name;
        try {
            this.watchlist = CSVParser.parseCSV(watchList);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
    public Watchlist(String name){
        this.name = name;
        this.watchlist = new ArrayList<>();
    }
    public Watchlist(WatchListGateway watchList){
        this.name = watchList.getName();
        this.watchlist = watchList.getWatchList();
    }
    public String getName() {
        return name;
    }
    public List<Movie> getWatchlist() {
        return watchlist;
    }
    public Movie getMovieByTitle(String name) {
        for (Movie movie : watchlist) {
            if (movie.getTitle().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        for (Movie movie : watchlist) {
            tags.add(movie.getIMDBTag());
        }
        return tags;
    }

    public Movie getMovieByTag(String tag) {
        for (Movie movie : watchlist) {
            if (movie.getIMDBTag().equals(tag)) {
                return movie;
            }
        }
        return null;
    }
    public List<Movie> getMovies() {
        return watchlist;
    }

    public void addMovie(Movie movie) {
        watchlist.add(movie);
    }
}

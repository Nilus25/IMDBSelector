import java.util.ArrayList;
import java.util.List;

public class WatchList {
    private List<Movie> watchList;
    public WatchList(List<String> watchList){
        for (String infoMovie : watchList) {
            String[] infoMovieSplitted = infoMovie.split(",");
            Movie movie = new Movie(infoMovieSplitted[5], infoMovieSplitted[1], infoMovieSplitted[8]);
            this.watchList.add(movie);
        }
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

import java.util.List;

public class UserGateway {
    private String name;
    private List<Movie> seenMovies;
    public void save() {

    }
    public void load() {

    }
    public void addMovie(Movie movie) {
        if (!seenMovies.contains(movie)) {
            seenMovies.add(movie);
        }
    }
    public List<Movie> getSeenMovies() {
        return seenMovies;
    }
}
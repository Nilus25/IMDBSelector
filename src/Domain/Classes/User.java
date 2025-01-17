package Domain.Classes;

import DataLayer.UserGateway;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Movie> seenMovies;
    public User(String name) {
        this.name = name;
        this.seenMovies = new ArrayList<>();
    }
    public User(UserGateway user) {
        this.name = user.getName();
        this.seenMovies = user.getSeenMovies();
    }

    public void addMovie(Movie movie) {
        if (!seenMovies.contains(movie)) {
            seenMovies.add(movie);
        }
    }
    public String getName() {
        return name;
    }
    public List<Movie> getSeenMovies() {
        return seenMovies;
    }


}

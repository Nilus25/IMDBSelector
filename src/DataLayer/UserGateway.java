package DataLayer;
import Domain.Classes.Movie;
import Domain.Classes.User;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class UserGateway {
    private String name;
    private List<Movie> seenMovies;
    private Movie lastMovieGenerated;
    private static final String location = "data/user";
    public UserGateway(String name) {
        this.name = name;
        this.seenMovies = new ArrayList<>();
        this.lastMovieGenerated = null;
    }
    public UserGateway(User user) {
        this.name = user.getName();
        this.seenMovies = user.getSeenMovies();
        this.lastMovieGenerated = user.getLastMovieGenerated();
    }

    public String getName() {
        return name;
    }
    public List<Movie> getSeenMovies() {
        return seenMovies;
    }
    public Movie getLastMovieGenerated() {
        return lastMovieGenerated;
    }
    public void save() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location + "/" + name + "_user.json")) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void delete() {
        try {
            Path filePath = Path.of(location + "/" + name + "_user.json");
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
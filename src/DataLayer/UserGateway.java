package DataLayer;
import Domain.Classes.Movie;
import Domain.Classes.User;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserGateway {
    private String name;
    private List<Movie> seenMovies;
    private static final String location = "data/user";
    public UserGateway(String name) {
        this.name = name;
        this.seenMovies = new ArrayList<>();
    }
    public UserGateway(User user) {
        this.name = user.getName();
        this.seenMovies = user.getSeenMovies();
    }

    public String getName() {
        return name;
    }

    public List<Movie> getSeenMovies() {
        return seenMovies;
    }


    public void save() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(location + "/" + name + "_user.json")) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
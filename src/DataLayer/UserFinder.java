package DataLayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class UserFinder {
    private static final String location = "/data/user";
    private static UserFinder instance;
    public static UserFinder getInstance() {
        if (instance == null) {
            instance = new UserFinder();
        }
        return instance;
    }
    public static UserGateway findUser(String name) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(location + "/" + name + "_user.json")) {
            Type type = new TypeToken<UserGateway>() {
            }.getType();
            UserGateway loadedUser = gson.fromJson(reader, type);
            return loadedUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package DataLayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserFinder {
    private static final String location = "data/user";
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
        } catch (FileNotFoundException e) {
            UserGateway newUser = new UserGateway(name); // Assuming UserGateway has a constructor that takes a name
            try (FileWriter writer = new FileWriter(location + "/" + name + "_user.json")) {
                Gson gsonWriter = new Gson();
                gsonWriter.toJson(newUser, writer);
                return newUser;
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<String> getAllUsersName() {
        List<String> users = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(location), "*_user.json")) {
            for (Path entry : stream) {
                String fileName = entry.getFileName().toString();
                String userName = fileName.substring(0, fileName.indexOf("_user.json"));
                users.add(userName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}

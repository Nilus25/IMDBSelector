package Domain;

import Domain.Classes.User;
import Domain.Classes.WatchList;
import Domain.Exceptions.MovieNotFoundException;
import Domain.Exceptions.UserAlreadyExistsException;
import Domain.Exceptions.WatchListAlreadyExistsException;

import java.util.List;

public interface Model {

    User getUser();

    WatchList getWatchList();

    void loadUser(String name);

    void loadWatchList(String name);

    void newWatchList(String name, List<String> movies) throws WatchListAlreadyExistsException;

    void newUser(String name) throws UserAlreadyExistsException;

    void actualizeWatchList(List<String> movies);

    void addMovieToSeen();

    void selectRandomMovie() throws MovieNotFoundException;

    void setModeRandom();

    void setModeNotSeen();

    boolean isThereCurrentMovie();

    boolean isThereWatchList();

    boolean isThereUser();

    List<String> getAllWatchListsName();

    List<String> getAllUsersName();

    String getCurrentMovie();
}

package Domain;

import Domain.Classes.FiltersDTO;
import Domain.Classes.User;
import Domain.Classes.Watchlist;
import Domain.Exceptions.MovieNotFoundException;
import Domain.Exceptions.UserAlreadyExistsException;
import Domain.Exceptions.WatchListAlreadyExistsException;
import Presentation.FiltersView;

import java.util.List;

public interface Model {

    User getUser();

    Watchlist getWatchlist();

    void loadUser(String name);

    void loadWatchList(String name);

    void filterWatchlist();

    FiltersDTO getFilters();

    void setFilters(FiltersDTO filters);

    void newWatchList(String name, String movies) throws WatchListAlreadyExistsException;

    void newUser(String name) throws UserAlreadyExistsException;

    void actualizeWatchList(String movies);

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

    String getLastMovieGenerated();

    void deleteWatchList();

    void deleteUser();

    void addLastMovieGeneratedForUserToSeen();

    List<String> getCurrentMovieInformation();

    FiltersView getFiltersView();

    Watchlist getFilteredWatchlist();
}

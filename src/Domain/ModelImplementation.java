package Domain;

import Domain.Classes.*;
import Domain.Exceptions.*;
import DataLayer.UserFinder;
import DataLayer.UserGateway;
import DataLayer.WatchListFinder;
import DataLayer.WatchListGateway;
import Presentation.FiltersView;
import Presentation.MainView;
import Presentation.MainViewController;

import java.util.ArrayList;
import java.util.List;

public class ModelImplementation implements Model {
    private User user;
    private List<String> usersNames;
    private Watchlist watchlist;
    private List<String> watchlistsNames;
    private final RandomMovieSelector selector;
    private Filter filter;
    private Watchlist filteredWatchlist;
    private Movie currentMovie;
    private MainView mainView;
    private FiltersView filtersView;
    private List<Observer> observers;
    private List<ObserverComboBoxes> observerComboBoxes;

    public ModelImplementation() {
        usersNames = UserFinder.getAllUsersName();
        watchlistsNames = WatchListFinder.getAllWatchListsName();
        observers = new ArrayList<>();
        observerComboBoxes = new ArrayList<>();
        mainView = new MainView(this);
        mainView.initializeController();
        selector = new RandomMovieSelector(this);
        filter = new Filter();
        filtersView = new FiltersView(this);
        filtersView.initializeController();
        addObserver(mainView);
        addObserverComboBox(mainView);
        notifyObservers();
    }

    private void addObserverComboBox(ObserverComboBoxes observer) {
        observerComboBoxes.add(observer);
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setUser(User user) {
        this.user = user;
        notifyObservers();
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
        if (watchlist != null) {
            System.out.println(watchlist.getName());
        }
        notifyObservers();
    }
    @Override
    public void loadUser(String name) {
        User user = new User(UserFinder.findUser(name));
        setUser(user);
        Movie lastMovieGenerated = user.getLastMovieGenerated();
        if (lastMovieGenerated != null && !user.LastMovieGeneratedAlreadySeen()) {
            mainView.throwLastMovieGeneratedForUser(lastMovieGenerated.getTitle(), lastMovieGenerated.getYear());
        }
        notifyObserverComboBoxes();
    }

    @Override
    public void loadWatchList(String name) {
        Watchlist watchlist = new Watchlist(WatchListFinder.findWatchList(name));
        filter = new Filter();
        filteredWatchlist = watchlist;
        setWatchlist(watchlist);
        notifyObserverComboBoxes();
    }

    @Override
    public void filterWatchlist() {
        filteredWatchlist = filter.filterWatchlist(watchlist);
    }
    @Override
    public FiltersDTO getFilters() {
        return filter.getFilters();
    }
    @Override
    public void setFilters(FiltersDTO filters) {
        filter.setFilters(filters);
    }

    @Override
    public void newWatchList(String name, String movies) throws WatchListAlreadyExistsException {
        if (watchlistsNames.contains(name)) {
            throw new WatchListAlreadyExistsException("Watchlist already exists");
        }
        Watchlist watchList = new Watchlist(name, movies);
        WatchListGateway gateway = new WatchListGateway(watchList);
        gateway.save();
        watchlistsNames.add(name);
        notifyObserverComboBoxes();
        notifyObservers();
    }

    @Override
    public void newUser(String name) throws UserAlreadyExistsException {
        if (usersNames.contains(name)) {
            throw new UserAlreadyExistsException("User already exists");
        }
        UserGateway gateway = new UserGateway(name);
        gateway.save();
        usersNames.add(name);
        notifyObserverComboBoxes();
        notifyObservers();
    }
    @Override
    public void actualizeWatchList(String movies) {
        String name = this.watchlist.getName();
        Watchlist watchlist = new Watchlist(name, movies);
        this.watchlist = watchlist;
        this.filteredWatchlist = filter.filterWatchlist(watchlist);
        WatchListGateway gateway = new WatchListGateway(watchlist);
        gateway.save();
        notifyObservers();
    }

    @Override
    public void addMovieToSeen() {
        user.addMovie(currentMovie);
        UserGateway gateway = new UserGateway(user);
        gateway.save();
    }
    public void addLastMovieGeneratedForUserToSeen() {
        user.addMovie(user.getLastMovieGenerated());
        UserGateway gateway = new UserGateway(user);
        gateway.save();
    }

    @Override
    public List<String> getCurrentMovieInformation() {
        return currentMovie.getInformation();
    }

    @Override
    public FiltersView getFiltersView() {
        return filtersView;
    }

    @Override
    public Watchlist getFilteredWatchlist() {
        return filteredWatchlist;
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    @Override
    public void selectRandomMovie() throws MovieNotFoundException {
        setCurrentMovie(selector.execute());
        user.setLastMovie(currentMovie);
        UserGateway gateway = new UserGateway(user);
        gateway.save();
        notifyObservers();
    }
    @Override
    public void setModeRandom() {
        selector.setModeRandom();
    }
    @Override
    public void setModeNotSeen() {
        selector.setModeNotSeen();
    }

    @Override
    public boolean isThereCurrentMovie() {
        return currentMovie != null;
    }
    @Override
    public boolean isThereWatchList() {
        return watchlist != null;
    }
    @Override
    public boolean isThereUser() {
        return user != null;
    }

    @Override
    public List<String> getAllWatchListsName() {
        return watchlistsNames;
    }

    @Override
    public List<String> getAllUsersName() {
        return usersNames;
    }

    @Override
    public String getCurrentMovie() {
        return currentMovie.getTitle();
    }
    @Override
    public String getLastMovieGenerated() {
        Movie lastMovieGenerated = user.getLastMovieGenerated();
        if (lastMovieGenerated == null) {
            return "";
        }
        return lastMovieGenerated.getTitle();
    }

    @Override
    public void deleteWatchList() {
        watchlistsNames.remove(watchlist.getName());
        WatchListGateway gateway = new WatchListGateway(watchlist);
        gateway.delete();
        watchlist = null;
        notifyObserverComboBoxes();
        notifyObservers();
    }

    @Override
    public void deleteUser() {
        usersNames.remove(user.getName());
        UserGateway gateway = new UserGateway(user);
        gateway.delete();
        user = null;
        notifyObserverComboBoxes();
        notifyObservers();
    }

    private void notifyObserverComboBoxes() {
        for (ObserverComboBoxes observer : observerComboBoxes) {
            observer.updateComboBoxes();
        }
    }
}

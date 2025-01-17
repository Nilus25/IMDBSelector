package Domain;

import Domain.Classes.Movie;
import Domain.Classes.RandomMovieSelector;
import Domain.Classes.User;
import Domain.Classes.WatchList;
import Domain.Exceptions.*;
import DataLayer.UserFinder;
import DataLayer.UserGateway;
import DataLayer.WatchListFinder;
import DataLayer.WatchListGateway;
import Presentation.MainView;
import Presentation.MainViewController;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private User user;
    private WatchList watchList;
    private RandomMovieSelector selector;
    private Movie currentMovie;
    private MainView mainView;
    private MainViewController mainViewController;
    private List<Observer> observers;

    public Model() {
        observers = new ArrayList<>();
        mainView = new MainView(this);
        mainViewController = mainView.initializeController();
        selector = new RandomMovieSelector(this);
        addObserver(mainView);
        addObserver(mainViewController);
        notifyObservers();
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
    public User getUser() {
        return user;
    }

    public WatchList getWatchList() {
        return watchList;
    }
    public void setUser(User user) {
        this.user = user;
        notifyObservers();
    }
    public void setWatchList(WatchList watchList) {
        this.watchList = watchList;
        notifyObservers();
    }
    public void loadUser(String name) {
        User user = new User(UserFinder.findUser(name));
        setUser(user);
    }

    public void loadWatchList(String name) {
        WatchList watchList = new WatchList(WatchListFinder.findWatchList(name));
        setWatchList(watchList);
    }

    public void newWatchList(String name, List<String> movies) throws WatchListAlreadyExistsException {
        WatchList watchList = new WatchList(name, movies);
        WatchListGateway gateway = new WatchListGateway(watchList);
        gateway.save();
        setWatchList(watchList);
    }

    public void newUser(String name) throws UserAlreadyExistsException {
        User user = new User(name);
        UserGateway gateway = new UserGateway(user);
        gateway.save();
        setUser(user);
    }
    public void actualizeWatchList(List<String> movies) {
        String name = watchList.getName();
        WatchList watchList = new WatchList(name, movies);
        WatchListGateway gateway = new WatchListGateway(watchList);
        gateway.save();
    }

    public void addMovieToSeen() {
        user.addMovie(currentMovie);
        UserGateway gateway = new UserGateway(user);
        gateway.save();
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public void selectRandomMovie() {
        setCurrentMovie(selector.execute());
        notifyObservers();
    }
    public void setModeRandom() {
        selector.setModeRandom();
    }
    public void setModeNotSeen() {
        selector.setModeNotSeen();
    }

    public boolean isThereCurrentMovie() {
        return currentMovie != null;
    }
    public boolean isThereWatchList() {
        return watchList != null;
    }
    public boolean isThereUser() {
        return user != null;
    }

    public List<String> getAllWatchListsName() {
        return WatchListFinder.getAllWatchListsName();
    }

    public List<String> getAllUsersName() {
        return UserFinder.getAllUsersName();
    }
}

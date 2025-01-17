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

public class ModelImplementation implements Model {
    private User user;
    private List<String> usersNames;
    private WatchList watchList;
    private List<String> watchListsNames;
    private RandomMovieSelector selector;
    private Movie currentMovie;
    private MainView mainView;
    private MainViewController mainViewController;
    private List<Observer> observers;

    public ModelImplementation() {
        usersNames = UserFinder.getAllUsersName();
        watchListsNames = WatchListFinder.getAllWatchListsName();
        observers = new ArrayList<>();
        mainView = new MainView(this);
        mainViewController = mainView.initializeController();
        selector = new RandomMovieSelector(this);
        addObserver(mainView);
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
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public WatchList getWatchList() {
        return watchList;
    }

    public void setUser(User user) {
        this.user = user;
        notifyObservers();
    }

    public void setWatchList(WatchList watchList) {
        this.watchList = watchList;
        if (watchList != null) {
            System.out.println(watchList.getName());
        }
        notifyObservers();
    }
    @Override
    public void loadUser(String name) {
        User user = new User(UserFinder.findUser(name));
        setUser(user);
        Movie lastMovieGenerated = user.getLastMovieGenerated();
        if (lastMovieGenerated != null) {
            setCurrentMovie(lastMovieGenerated);
        }
    }

    @Override
    public void loadWatchList(String name) {
        WatchList watchList = new WatchList(WatchListFinder.findWatchList(name));
        setWatchList(watchList);
    }

    @Override
    public void newWatchList(String name, String movies) throws WatchListAlreadyExistsException {
        if (watchListsNames.contains(name)) {
            throw new WatchListAlreadyExistsException("WatchList already exists");
        }
        WatchList watchList = new WatchList(name, movies);
        WatchListGateway gateway = new WatchListGateway(watchList);
        gateway.save();
        watchListsNames.add(name);
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
        String name = watchList.getName();
        WatchList watchList = new WatchList(name, movies);
        WatchListGateway gateway = new WatchListGateway(watchList);
        gateway.save();
    }

    @Override
    public void addMovieToSeen() {
        user.addMovie(currentMovie);
        UserGateway gateway = new UserGateway(user);
        gateway.save();
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
        return watchList != null;
    }
    @Override
    public boolean isThereUser() {
        return user != null;
    }

    @Override
    public List<String> getAllWatchListsName() {
        return watchListsNames;
    }

    @Override
    public List<String> getAllUsersName() {
        return usersNames;
    }

    @Override
    public String getCurrentMovie() {
        return currentMovie.getName();
    }
    @Override
    public String getLastMovieGenerated() {
        Movie lastMovieGenerated = user.getLastMovieGenerated();
        if (lastMovieGenerated == null) {
            return "";
        }
        return lastMovieGenerated.getName();
    }

    @Override
    public void deleteWatchList() {
        watchListsNames.remove(watchList.getName());
        WatchListGateway gateway = new WatchListGateway(watchList);
        gateway.delete();
        watchList = null;
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
        for (Observer observer : observers) {
            observer.updateComboBoxes();
        }
    }
}

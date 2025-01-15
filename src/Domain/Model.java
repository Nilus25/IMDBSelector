package Domain;

import DataLayer.UserFinder;
import DataLayer.UserGateway;
import DataLayer.WatchListFinder;
import DataLayer.WatchListGateway;

import java.util.List;

public class Model {
    private User user;
    private WatchList watchList;
    private RandomMovieSelector selector;
    private Movie currentMovie;
    public Model() {
        selector = new RandomMovieSelector(this);
    }
    public User getUser() {
        return user;
    }

    public WatchList getWatchList() {
        return watchList;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void setWatchList(WatchList watchList) {
        this.watchList = watchList;
    }
    public void loadUser(String name) {
        User user = new User(UserFinder.findUser(name));
        setUser(user);
    }

    public void loadWatchList(String name) {
        WatchList watchList = new WatchList(WatchListFinder.findWatchList(name));
        setWatchList(watchList);
    }

    public void newWatchList(String name, List<String> movies) {
        WatchList watchList = new WatchList(name, movies);
        WatchListGateway gateway = new WatchListGateway(watchList);
        gateway.save();
        setWatchList(watchList);
    }

    public void newUser(String name) {
        User user = new User(name);
        setUser(user);
    }

    public void addMovieToSeen(Movie currentMovie) {
        user.addMovie(currentMovie);
        UserGateway gateway = new UserGateway(user);
        gateway.save();
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public void selectRandomMovie() {
        setCurrentMovie(selector.execute());
    }
}

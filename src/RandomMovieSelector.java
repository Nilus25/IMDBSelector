import java.util.List;

public class RandomMovieSelector{
    SelectorMode mode;
    UserGateway userGateway;
    Movie currentMovie;
    WatchList watchList;
    public void execute() {
        String tag = mode.selectRandomMovie();
        currentMovie = watchList.getMovieByTag(tag);
        URLOpener.openURL(currentMovie.getURL());
    }
    public void setModeRandom() {
        mode = new RandomSelectorMode(this);
    }
    public void setModeNotSeen() {
        mode = new NotSeenSelectorMode(this);
    }
    public void setUser(String name) {
        userGateway= UserFinder.getInstance().findUser(name);
    }
    public void addSeenMovie() {
        userGateway.addMovie(currentMovie);
        userGateway.save();
    }
    public void setWatchList(List<String> watchList) {
        this.watchList = new WatchList(watchList);
    }
    public WatchList getWatchList() {
        return watchList;
    }
    public UserGateway getUserGateway() {
        return userGateway;
    }
}

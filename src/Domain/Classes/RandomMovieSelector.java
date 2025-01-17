package Domain.Classes;

import Domain.*;
import Domain.Exceptions.MovieNotFoundException;

public class RandomMovieSelector{
    private Model model;
    SelectorMode mode;
    public RandomMovieSelector(Model model){
        this.model = model;
        mode = new RandomSelectorMode(this);
    }
    public Movie execute() throws MovieNotFoundException {
        String tag = mode.selectRandomMovie();
        Movie currentMovie = model.getWatchList().getMovieByTag(tag);
        URLOpener.openURL(currentMovie.getURL());
        return currentMovie;
    }
    public void setModeRandom() {
        mode = new RandomSelectorMode(this);
    }
    public void setModeNotSeen() {
        mode = new NotSeenSelectorMode(this);
    }

    public WatchList getWatchList() {
        return model.getWatchList();
    }
    public User getUser() {
        return model.getUser();
    }
}

package Domain.Classes;

import Domain.Exceptions.MovieNotFoundException;

import java.util.List;

public abstract class SelectorModeAbstract implements SelectorMode {
    protected RandomMovieSelector randomMovieSelector;
    public SelectorModeAbstract(RandomMovieSelector randomMovieSelector) {
        this.randomMovieSelector = randomMovieSelector;
    }
    @Override
    public String selectRandomMovie() throws MovieNotFoundException {
        WatchList watchList = randomMovieSelector.getWatchList();
        List<String> watchListTags = watchList.getTags();
        netejaMode(watchListTags);
        if (watchListTags.isEmpty()) {
            throw new MovieNotFoundException("No movies found fulfilling the criteria");
        }
        return watchListTags.get((int) (Math.random() * watchListTags.size()));
    }

}

package Domain;

import java.util.List;

public abstract class SelectorMode {
    protected RandomMovieSelector randomMovieSelector;
    public SelectorMode(RandomMovieSelector randomMovieSelector) {
        this.randomMovieSelector = randomMovieSelector;
    }
    public String selectRandomMovie(){
        WatchList watchList = randomMovieSelector.getWatchList();
        List<String> watchListTags = watchList.getTags();
        netejaMode(watchListTags);
        return watchListTags.get((int) (Math.random() * watchListTags.size()));
    }

    public abstract void netejaMode(List<String> watchListTags);
}

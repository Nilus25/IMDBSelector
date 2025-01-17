package Domain.Classes;

import java.util.List;

public class NotSeenSelectorMode extends SelectorModeAbstract {
    public NotSeenSelectorMode(RandomMovieSelector randomMovieSelector) {
        super(randomMovieSelector);
    }

    @Override
    public void netejaMode(List<String> watchListTags) {
        List<Movie> seenMovies = randomMovieSelector.getUser().getSeenMovies();
        for (Movie movie : seenMovies) {
            watchListTags.remove(movie.getIMDBTag());
        }
    }
}

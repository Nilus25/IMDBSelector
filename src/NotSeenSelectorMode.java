import java.util.List;

public class NotSeenSelectorMode extends SelectorMode {
    public NotSeenSelectorMode(RandomMovieSelector randomMovieSelector) {
        super(randomMovieSelector);
    }

    @Override
    public void netejaMode(List<String> watchListTags) {
        List<Movie> seenMovies = randomMovieSelector.getUserGateway().getSeenMovies();
        for (Movie movie : seenMovies) {
            watchListTags.remove(movie.getIMDBTag());
        }
    }
}

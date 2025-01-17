package Domain.Classes;

import Domain.Exceptions.MovieNotFoundException;

import java.util.List;

public interface SelectorMode {
    String selectRandomMovie() throws MovieNotFoundException;

    void netejaMode(List<String> watchListTags);
}

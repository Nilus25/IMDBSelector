package Domain.Classes;

import java.time.Year;
import java.util.*;

public class Filter {
    public static final Double MINIMUMRATING = 0.;
    public static final Double MAXIMUMRATING = 10.;
    private Double minimumRatingValue = MINIMUMRATING;
    private Double maximumRatingValue = MAXIMUMRATING;
    public static final int MINIMUMYEAR = 1890;
    public static final int MAXIMUMYEAR = Year.now().getValue();
    private int minimumYearValue = MINIMUMYEAR;
    private int maximumYearValue = MAXIMUMYEAR;
    private boolean[] checkedTypes;
    private boolean[] checkedGenres;
    private final Map<String, Integer> types;
    private final Map<String, Integer> genres;
    private List<String> keywords = new ArrayList<>();
    public static final String[] GENREARRAY = {"Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary",
            "Drama", "Family", "Fantasy", "Film-Noir", "Game-Show", "History", "Horror", "Music", "Musical", "Mystery",
            "News", "Reality-TV", "Romance", "Sci-Fi", "Short", "Sport", "Talk-Show", "Thriller", "War", "Western"};
    public static final String[] TYPEARRAY = {"Movie", "TV Series", "Short", "TV Episode", "TV Mini Series", "TV Movie",
            "TV Special", "TV Short", "Video Game", "Video", "Music Video", "Podcast Series", "Podcast Episode"};

    public Filter() {
        types = new HashMap<>();
        genres = new HashMap<>();
        initializeTypes();
        initializeGenres();
    }

    private void initializeTypes() {
        checkedTypes = new boolean[TYPEARRAY.length];
        for (int i = 0; i < TYPEARRAY.length; i++) {
            types.put(TYPEARRAY[i], i);
            checkedTypes[i] = true;
        }
    }

    private void initializeGenres() {
        checkedGenres = new boolean[GENREARRAY.length];
        for (int i = 0; i < GENREARRAY.length; i++) {
            genres.put(GENREARRAY[i], i);
            checkedGenres[i] = true;
        }
    }

    public Double getMinimumRating() {
        return MINIMUMRATING;
    }

    public Double getMaximumRating() {
        return MAXIMUMRATING;
    }

    public Double getMinimumRatingValue() {
        return minimumRatingValue;
    }

    public Double getMaximumRatingValue() {
        return maximumRatingValue;
    }

    public int getMinimumYear() {
        return MINIMUMYEAR;
    }

    public int getMaximumYear() {
        return MAXIMUMYEAR;
    }

    public int getMinimumYearValue() {
        return minimumYearValue;
    }

    public int getMaximumYearValue() {
        return maximumYearValue;
    }

    public boolean[] getCheckedTypes() {
        return checkedTypes;
    }

    public boolean[] getCheckedGenres() {
        return checkedGenres;
    }

    public String[] getGenreArray() {
        return GENREARRAY;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public String[] getTypeArray() {
        return TYPEARRAY;
    }

    public void setMinimumRatingValue(Double minimumRatingValue) {
        this.minimumRatingValue = minimumRatingValue;
    }

    public void setMaximumRatingValue(Double maximumRatingValue) {
        this.maximumRatingValue = maximumRatingValue;
    }

    public void setMinimumYearValue(int minimumYearValue) {
        this.minimumYearValue = minimumYearValue;
    }

    public void setMaximumYearValue(int maximumYearValue) {
        this.maximumYearValue = maximumYearValue;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setCheckedGenres(boolean[] checkedGenres) {
        this.checkedGenres = checkedGenres;
    }

    public void setCheckedTypes(boolean[] checkedTypes) {
        this.checkedTypes = checkedTypes;
    }
    public Watchlist filterWatchlist(Watchlist watchList) {
        Watchlist filteredWatchlist = watchList;
        if (!Objects.equals(maximumRatingValue, MAXIMUMRATING) || !Objects.equals(minimumRatingValue, MINIMUMRATING)) {
            filteredWatchlist = filterByRating(filteredWatchlist);
        }
        if (maximumYearValue != MAXIMUMYEAR || minimumYearValue != MINIMUMYEAR) {
            filteredWatchlist = filterByYear(filteredWatchlist);
        }
        boolean containsFalse = false;
        for (boolean checked : checkedGenres) {
            if (!checked) {
                containsFalse = true;
                break;
            }
        }
        if (containsFalse) {
            filteredWatchlist = filterByGenres(filteredWatchlist);
        }
        containsFalse = false;
        for (boolean checked : checkedTypes) {
            if (!checked) {
                containsFalse = true;
                break;
            }
        }
        if (containsFalse) {
            filteredWatchlist = filterByTypes(filteredWatchlist);
        }
        if (!keywords.isEmpty()) {
            filteredWatchlist = filterByKeywords(filteredWatchlist);
        }
        return filteredWatchlist;
    }

    private Watchlist filterByKeywords(Watchlist filteredWatchlist) {
        Watchlist newWatchlist = new Watchlist("Filtered Watchlist");
        for (Movie movie : filteredWatchlist.getMovies()) {
            for (String keyword : keywords) {
                String lowerCaseKeyword = keyword.toLowerCase();
                if (movie.getTitle().toLowerCase().contains(lowerCaseKeyword) ||
                        movie.getDirector().toLowerCase().contains(lowerCaseKeyword)) {
                    newWatchlist.addMovie(movie);
                    break;
                }
            }
        }
        return newWatchlist;
    }

    private Watchlist filterByTypes(Watchlist filteredWatchlist) {
        Watchlist newWatchlist = new Watchlist("Filtered Watchlist");
        for (Movie movie : filteredWatchlist.getMovies()) {
            if (checkedTypes[types.get(movie.getType())]) {
                newWatchlist.addMovie(movie);
            }
        }
        return newWatchlist;
    }

    private Watchlist filterByGenres(Watchlist filteredWatchlist) {
        Watchlist newWatchlist = new Watchlist("Filtered Watchlist");
        for (Movie movie : filteredWatchlist.getMovies()) {
            for (String genre : movie.getGenres()) {
                if (checkedGenres[genres.get(genre)]) {
                    newWatchlist.addMovie(movie);
                    break;
                }
            }
        }
        return newWatchlist;
    }

    private Watchlist filterByYear(Watchlist filteredWatchlist) {
        Watchlist newWatchlist = new Watchlist("Filtered Watchlist");
        for (Movie movie : filteredWatchlist.getMovies()) {
            int year = Integer.parseInt(movie.getYear());
            if (year >= minimumYearValue && year <= maximumYearValue) {
                newWatchlist.addMovie(movie);
            }
        }
        return newWatchlist;
    }

    private Watchlist filterByRating(Watchlist filteredWatchlist) {
        Watchlist newWatchlist = new Watchlist("Filtered Watchlist");
        for (Movie movie : filteredWatchlist.getMovies()) {
            Double rating = Double.valueOf(movie.getRating());
            if (rating >= minimumRatingValue && rating <= maximumRatingValue) {
                newWatchlist.addMovie(movie);
            }
        }
        return newWatchlist;
    }

    public FiltersDTO getFilters() {
        return new FiltersDTO(checkedTypes, checkedGenres, minimumYearValue, maximumYearValue, minimumRatingValue, maximumRatingValue);
    }

    public void setFilters(FiltersDTO filters) {
        checkedTypes = filters.getCheckedTypes();
        checkedGenres = filters.getCheckedGenres();
        minimumYearValue = filters.getMinimumYearValue();
        maximumYearValue = filters.getMaximumYearValue();
        minimumRatingValue = filters.getMinimumRatingValue();
        maximumRatingValue = filters.getMaximumRatingValue();
    }
}

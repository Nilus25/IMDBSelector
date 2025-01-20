package Domain.Classes;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String IMDBTag;
    private String URL;
    private String director;
    private String year;
    private String rating;
    private String type;
    private List<String> genres;
    public String getTitle() {
        return title;
    }
    public String getIMDBTag() {
        return IMDBTag;
    }
    public String getURL() {
        return URL;
    }
    public String getDirector() {
        return director;
    }
    public String getYear() {
        return year;
    }
    public String getRating() {
        return rating;
    }
    public String getType() {
        return type;
    }
    public List<String> getGenres() {
        return genres;
    }

    public Movie(String title, String IMDBTag, String URL, String director, String year, String rating, String type, List<String> genres) {
        this.title = title;
        this.IMDBTag = IMDBTag;
        this.URL = URL;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.type = type;
        this.genres = genres;
    }
   public List<String> movieToString() {
        List<String> movie = new ArrayList<>();
        movie.add(title);
        movie.add(IMDBTag);
        movie.add(URL);
        movie.add(director);
        movie.add(year);
        movie.add(rating);
        movie.add(type);
       movie.addAll(genres);
        return movie;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Movie movie = (Movie) obj;
        return IMDBTag.equals(movie.IMDBTag);
    }

    @Override
    public int hashCode() {
        return IMDBTag.hashCode();
    }

    public List<String> getInformation() {
        List<String> information = new ArrayList<>();
        information.add(title);
        information.add(director);
        information.add(year);
        information.add(rating);
        information.add(type);
        return information;
    }
}

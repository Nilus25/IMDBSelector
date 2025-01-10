public class Movie {
    private String title;
    private String IMDBTag;
    private String URL;
    public String getName() {
        return title;
    }

    public String getIMDBTag() {
        return IMDBTag;
    }
    public String getURL() {
        return URL;
    }

    public Movie(String title, String IMDBTag, String URL) {
        this.title = title;
        this.IMDBTag = IMDBTag;
        this.URL = URL;
    }

}

package Domain.Classes;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static List<Movie> parseCSV(String csvData) throws IOException, CsvValidationException {
        List<Movie> movies = new ArrayList<>();
        CSVReader reader = new CSVReader(new StringReader(csvData));
        String[] nextLine;

        reader.readNext();

        while ((nextLine = reader.readNext()) != null) {
            String title = nextLine[5];
            String IMDBTag = nextLine[1];
            String URL = nextLine[7];
            String director = nextLine[15];
            String year = nextLine[11];
            String rating = nextLine[9];
            String type = nextLine[8];
            List<String> genres = List.of(nextLine[12].split(","));

            Movie movie = new Movie(title, IMDBTag, URL, director, year, rating, type, genres);
            movies.add(movie);
        }

        return movies;
    }
}

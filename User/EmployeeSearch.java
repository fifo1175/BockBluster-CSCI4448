package User;

import Store.Movie;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.apache.commons.text.WordUtils;
import org.json.JSONException;

import java.util.Scanner;

public class EmployeeSearch implements SearchStrategy {
    public Movie search(String movie, ) { //needs store class
        HttpResponse<JsonNode> searchResult = store.MovieSearch(movie);

        System.out.println("Select an option below:");

        org.json.JSONObject result = null;
        org.json.JSONArray resultArray = new org.json.JSONArray();
        try {
            resultArray = searchResult.getBody().getObject().getJSONArray("Search");
        } catch (JSONException e) {
            System.out.println("\'" + movie + "\' produced no results.");
        }

        for (int i = 0; i < (resultArray.length()) / 2; i++) {
            //System.out.println(results.getJSONObject(1));

            result = resultArray.getJSONObject(i);
            String title = result.getString("Title");
            String year = result.getString("Year");

            System.out.println(i + 1 + ": " + title + " (" + year + ")");
        }

        System.out.println("6: Return to Customer Home Page");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            int searchChoice = scanner.nextInt();

            switch (searchChoice) {
                case 1:
                    result = resultArray.getJSONObject(0);
                    break;
                case 2:
                    result = resultArray.getJSONObject(1);
                    break;
                case 3:
                    result = resultArray.getJSONObject(2);
                    break;
                case 4:
                    result = resultArray.getJSONObject(3);
                    break;
                case 5:
                    result = resultArray.getJSONObject(4);
                    break;
                case 6:
                    return 1;
                default:
                    System.out.println("Invalid input; please enter option 1-6");
                    break;
            }

            Movie resultMovie = GetMovie(result.getString("imdbID"));

            System.out.println("Title: " + resultMovie.title);
            System.out.println("Release Year: " + resultMovie.year);
            System.out.println("Film Rating: " + resultMovie.filmRating);
            System.out.println("Runtime: " + resultMovie.runtime);
            System.out.println("IMDb Rating: " + resultMovie.imdbRating);
            System.out.println("Genre: " + resultMovie.genre);
            System.out.println("Plot: " + WordUtils.wrap(resultMovie.plot, 90));
            System.out.println("Director: " + resultMovie.director);
            System.out.println("Actors: " + resultMovie.actors);
            System.out.println("Country: " + resultMovie.country);
            return 1;
        }
    }
}

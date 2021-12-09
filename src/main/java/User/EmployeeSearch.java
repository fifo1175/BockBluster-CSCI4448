package User;

import Store.Movie;
import Store.Store;

// JSON imports
import org.json.JSONException;

// API imports
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;


import org.apache.commons.text.WordUtils;

import java.util.Scanner;

import User.CustomerSearch;


public class EmployeeSearch implements SearchStrategy {
    public String movie;
    public Store store;
    public User user;

    public EmployeeSearch(String movie, Store store, User user) {
        this.movie = movie;
        this.store = store;
        this.user = user;
    }
    

    @Override
    public Movie search(String movie, Store store, User user) { //needs store class
        HttpResponse<JsonNode> searchResult = null;
        try {
            searchResult = store.MovieSearch(movie);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

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
                default:
                    System.out.println("Invalid input; please enter option 1-6");
                    break;
            }

            Movie resultMovie = null;
            try {
                resultMovie = store.GetMovie(result.getString("imdbID"));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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
            
            // movie is added to store's inventory

            store.moviesInStock.add(resultMovie);

            System.out.println();
            System.out.println(resultMovie.title + " was added to the store's inventory!");
        
            return resultMovie;
        }
    }
}

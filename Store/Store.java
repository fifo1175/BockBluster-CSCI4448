package Store;

// JSON imports
import org.json.JSONException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;

// API imports
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.apache.commons.text.WordUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import User.User;
import User.UserFactory;

public class Store {
    public ArrayList<Movie> moviesInStock;
    public ArrayList<Movie> movieOnShelf;
    public ArrayList<Poster> postersInStock;

    public void DisplayUI(List<String> strings) {
        for (int i = 0; i < 100; i++) {
            System.out.print("-");
        }
        for(int i = 0; i < 15; i++) {
            System.out.println();
            System.out.print("|");

            String str = strings.get(i); // get next string in array
            Integer length = str.length();
            int half = Math.floorDiv(length, 2);

            // 49 spaces to the center, so  take half of the string, and start it that many spaces away from 49
            int start = 49 - half;  // the number of spaces in that string should start

            for (int j = 0; j < start; j++) {
                System.out.printf(" ");
            }
            System.out.print(str);
            float floatLength = length.floatValue();
            if(floatLength / 2 > half) {  // if it half was rounded down, we need 1 less space printed after the string since it's length is 1 more than half * 2
                start = start - 1;
            }
            for (int j = 0; j < start; j++) {
                System.out.printf(" ");
            }
            System.out.print("|");
        }
        System.out.println();
        for (int i = 0; i < 100; i++) {
            System.out.print("-");
        }
        System.out.println();

    }

    public void fetchJSONFromFile () {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("testData.json")){

            // read JSON file
            Object obj = parser.parse(reader);

            org.json.JSONArray movieList =  (org.json.JSONArray) obj;

            System.out.println(movieList);

            org.json.JSONObject result = null;

            result = movieList.getJSONObject(0);

            String title = result.getString("Title");
            String imdbID = result.getString("imdbRating");
            String year = result.getString("Year");
            String filmRating = result.getString("Rated");
            String runtime = result.getString("Runtime");
            String imdbRating = result.getString("imdbRating");
            String genre = result.getString("Genre");
            String plot = result.getString("Plot");
            String director = result.getString("Director");
            String actors = result.getString("Actors");
            String country = result.getString("Country");

            Movie resultMovie = new Movie(title, imdbID, year, filmRating, runtime, imdbRating, genre, plot, director,
                    actors, country);

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


            
            /*
            for(Object object : movieList) {
                JSONObject movieJSON = (JSONObject) object;  // create json objects from that array

                String title = (String) movieJSON.get("Title");

                System.out.println(title);
            }
            */
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block`
            e.printStackTrace();
        }
    }



    public void DisplayEmployeeUI() { // may not even need this, can just use 1 UI function and change the strings that go into it

    }

    public HttpResponse<JsonNode> MovieSearch(String title) throws Exception {
        /**
         * Use this method to obtain a list of movies related to the user's input
         * @param title Movie title to be searched
         * @return prettyJsonString JSON-formatted output containing search results
         */
        // Host url
        String host = "https://movie-database-imdb-alternative.p.rapidapi.com/";
        String charset = "UTF-8";
        // Headers for a request
        String x_rapidapi_host = "movie-database-imdb-alternative.p.rapidapi.com";
        String x_rapidapi_key = "3c14721fddmsh6282e2acc432c95p14d022jsn00a8f4c7f50c";

        // Format query for preventing encoding problems
        String query = String.format("s=%s",
                URLEncoder.encode(title, charset));

        HttpResponse<JsonNode> response = Unirest.get(host + "?" + query + "&type=movie")
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();

        return response;
    }

    public Movie GetMovie(String movieID) throws Exception {
        /**
         * Use this method to obtain movie details based on a movie ID
         * @param movieID IMDb movie ID (e.g. tt0111161)
         * @return prettyJsonString JSON-formatted output containing movie details
         */
        // Host url
        String host = "https://movie-database-imdb-alternative.p.rapidapi.com/";
        String charset = "UTF-8";
        // Headers for a request
        String x_rapidapi_host = "movie-database-imdb-alternative.p.rapidapi.com";
        String x_rapidapi_key = "3c14721fddmsh6282e2acc432c95p14d022jsn00a8f4c7f50c";

        // Format query for preventing encoding problems
        String query = String.format("i=%s",
                URLEncoder.encode(movieID, charset));

        HttpResponse<JsonNode> response = Unirest.get(host + "?" + query)
                .header("x-rapidapi-host", x_rapidapi_host)
                .header("x-rapidapi-key", x_rapidapi_key)
                .asJson();

        org.json.JSONObject result = response.getBody().getObject();

        String title = result.getString("Title");
        String imdbID = result.getString("imdbRating");
        String year = result.getString("Year");
        String filmRating = result.getString("Rated");
        String runtime = result.getString("Runtime");
        String imdbRating = result.getString("imdbRating");
        String genre = result.getString("Genre");
        String plot = result.getString("Plot");
        String director = result.getString("Director");
        String actors = result.getString("Actors");
        String country = result.getString("Country");

        Movie resultMovie = new Movie(title, imdbID, year, filmRating, runtime, imdbRating, genre, plot, director,
                actors, country);


        return resultMovie;
    }

    public ArrayList<Movie> GenreSearch(String Genre) {  // search API for movies by genre, return 5 of that genre
        return null;
    }

    public static void runSimulation() throws Exception {

        Store store = new Store();
        int choice = store.loginScreen();
        int choice1 = 1;
        int choice2 = 1;
        int customerMenuChoice = 1;
        UserFactory factory = new UserFactory();

        User customer = factory.getUser("Customer");

        while (choice != 0 && choice1 != 0 && choice2 != 0 && customerMenuChoice != 0) {
            if(choice == 1) {  // at some point, redo this choice logic so it doesn't become a huge nested if statement block
                
                choice1 = store.customerMenu();
                customerMenuChoice = store.runCustomer(choice1, customer);
            }
            else if(choice == 2) {
                factory.getUser("Employee");
                choice2 = store.employeeMenu();
            }
            else {
                return;
            }
        }

        return;
        //store.DisplayCustomerUI(strings);
    }

    public int loginScreen() {

        List<String> strings = new ArrayList<String>();
        strings.add("");
        strings.add("Welcome to BockBluster Movie Rental and Recommendations!");
        strings.add("");
        strings.add("Use the number keys to navigate the menu");
        strings.add("");
        strings.add("Press 1 if you're a customer");
        strings.add("Press 2 if you're an employee");
        strings.add("Press 0 if you're just passing by!");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("We hope you enjoy our store!");
        strings.add("");
        strings.add("");
        strings.add("");
        DisplayUI(strings);

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        return choice;
    }

    public int customerMenu() {
        List<String> strings = new ArrayList<String>();
        strings.add("");
        strings.add("Home page for Customer");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("Press 1 if you'd like to search for a movie");
        strings.add("Press 2 if you'd like to get some movie recommendations");
        strings.add("Press 3 if you'd like to checkout");
        strings.add("Press 4 if you'd like to wait around and see what happens in the store");
        strings.add("");
        strings.add("");
        strings.add("Press 0 to exit the store");
        strings.add("");
        strings.add("");
        strings.add("");
        DisplayUI(strings);

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();


        return choice;
    }

    public int runCustomer(int choice, User customer) throws Exception {
        if (choice == 1) {
            List<String> strings = new ArrayList<String>();
            strings.add("");
            strings.add("Movie Search");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("Please enter the title of the movie you wish to search for:");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            DisplayUI(strings);
            Scanner scanner = new Scanner(System.in);
            String movieTitle = scanner.nextLine();
 
            // this.fetchJSONFromFile();

            HttpResponse<JsonNode> searchResult = this.MovieSearch(movieTitle);

            System.out.println("Select an option below:");

            org.json.JSONObject result = null;
            org.json.JSONArray resultArray = new org.json.JSONArray();
            try {
                resultArray = searchResult.getBody().getObject().getJSONArray("Search");
            } catch (JSONException e) {
                System.out.println("\'" + movieTitle + "\' produced no results.");
            }

            for (int i = 0; i < (resultArray.length()) / 2; i++) {
                //System.out.println(results.getJSONObject(1));

                result = resultArray.getJSONObject(i);
                String title = result.getString("Title");
                String year = result.getString("Year");

                System.out.println(i + 1 + ": " + title + " (" + year + ")");
            }

            System.out.println("6: Return to Customer Home Page");

            while(true) {
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

                customer.cart.add(resultMovie);

                return 1;
            }
        }
        if (choice == 2) {
            List<String> strings = new ArrayList<String>();
            strings.add("");
            strings.add("Movie Recommendations");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("Press 1 for Action movie recommendations");
            strings.add("Press 2 for Comedy movie recommendations");
            strings.add("Press 3 for Drama movie recommendations");
            strings.add("Press 4 for Sci-fi movie recommendations");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            DisplayUI(strings);
            Scanner scanner = new Scanner(System.in);
            int movieGenre = scanner.nextInt();
            if(movieGenre == 1){
                this.GenreSearch("Action");
            }
            if(movieGenre == 2){
                this.GenreSearch("Comedy");
            }
            if(movieGenre == 3){
                this.GenreSearch("Drama");
            }
            if(movieGenre == 4){
                this.GenreSearch("Sci-fi");
            }

        }
        if (choice == 3) { // checkout

            List<String> titles = new ArrayList<String>();

            int numInCart = customer.cart.size();

            for (int i = 0; i < numInCart; i++) {
                
                titles.add(customer.cart.get(i).title);

            }

            for (int i = numInCart; i < 5; i++) {
                titles.add("");
            }

            List<String> strings = new ArrayList<String>();
            strings.add("");
            strings.add("Customer Checkout");
            strings.add("");
            strings.add("");
            strings.add("The movies you currently have in your cart are:");
            strings.add(titles.get(0));
            strings.add(titles.get(1));
            strings.add(titles.get(2));
            strings.add(titles.get(3));
            strings.add(titles.get(4));
            strings.add("");
            strings.add("");
            strings.add("Press 1 if you'd like confirm checkout");
            strings.add("Press 2 to go back");
            strings.add("");
            DisplayUI(strings);
            Scanner scanner = new Scanner(System.in);
            int confirm = scanner.nextInt();
            if(confirm == 1) {
                strings.clear();
                strings.add("");
                strings.add("Thank you!");
                strings.add("");
                strings.add("");
                strings.add("You checked out:");
                strings.add(titles.get(0));
                strings.add(titles.get(1));
                strings.add(titles.get(2));
                strings.add(titles.get(3));
                strings.add(titles.get(4));
                strings.add("");
                strings.add("");
                strings.add("Have a good day! We hope to see you again soon.");
                strings.add("");
                strings.add("");
                DisplayUI(strings);
                return 0;  // full exit
            }
            else{
                return 1; // return to menu6
            }

        }
        return 0;
    }

    public int employeeMenu() {
        List<String> strings = new ArrayList<String>();
        strings.add("");
        strings.add("Home page for Employee");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("Press 1 if you'd like to search for a movie to order");
        strings.add("Press 2 if you'd like to stock the shelves with movies you've ordered");
        strings.add("Press 3 if you'd like to search for a poster to order");
        strings.add("Press 4 if you'd like to put up the posters you've ordered");
        strings.add("Press 5 if you'd like to wait around and see what happens in the store");
        strings.add("");
        strings.add("Press 0 to exit the store");
        strings.add("");
        strings.add("");
        strings.add("");
        DisplayUI(strings);

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        return choice;
    }




}

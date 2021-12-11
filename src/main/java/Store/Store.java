package Store;

// -------------------------------------------------------------------------------------------------------------------------
// TO DO:
// put movie search funcitonality into employeeSearch and customerSearch for strategy pattern
// ----> DONE

// initialize empoloyee object before login functionality, so open store can be called everytime as an employee method
// ----> is open store still needed? what would it do?

// implement singleton pattern with store
// ----> DONE

// 3/4 patterns done, just need decorator

// poster search same as movie search, just create a poster object at the end
// ----> DONE

// events with decorator



// AFTER all functionality, we can add database for posters, maybe display on a localhost site

// JSON imports
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


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
import java.util.concurrent.ThreadLocalRandom;

import User.CustomerSearch;
import User.EmployeeSearch;
import User.PosterSearch;
import User.User;
import User.UserFactory;

import Events.ArgumentBadResult;
import Events.AgreementGoodResult;
import Events.Agreement;
import Events.Argument;


public class Store {
    public ArrayList<Movie> moviesInStock;  // movies orderd
    public ArrayList<Movie> movieOnShelf;  // movies already on shelf
    public ArrayList<Poster> postersInStock;
    private static Store single_instance = null;  // singleton object instance

    private Store(){                              // singleton private constructor
        moviesInStock = new ArrayList<Movie>();
        moviesInStock = new ArrayList<Movie>();
        postersInStock = new ArrayList<Poster>();
    }

    public static Store getInstance() {           // singleton get instance
        if(single_instance == null) {
            single_instance = new Store();
        }
        return single_instance;
    }

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
        String imdbID = result.getString("imdbID");
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

    public Poster GetPoster(String movieID) throws Exception {
        /**
         * Use this method to obtain a poster object based on a movie ID
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
        String year = result.getString("Year");
        String URL = result.getString("Poster");
        

        Poster resultPoster = new Poster(title, year, URL);


        return resultPoster;
    }

    public void GenreSearch(String Genre) throws Exception {  // search API for movies by genre, return 5 of that genre
        ArrayList<Movie> recommendedMovies = new ArrayList<>();
        System.out.println("Loading recommendations...");

        String connectionString = "mongodb+srv://movieDB:csci4448@cluster0.pfn8i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("bock_bluster");
            MongoCollection<Document> recommendations = database.getCollection("recommendations");

            System.out.println("Here is a list of movies that you should watch:");
            System.out.println("");

            if (Genre == "Action") {
                FindIterable<Document> movieRecommendations = recommendations.find(eq("Genre", "Action"));

                for (Document recommendation : movieRecommendations) {
                    String jsonString = recommendation.toJson();
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println(json.toString(4));
                }

            } else if (Genre == "Comedy") {
                FindIterable<Document> movieRecommendations = recommendations.find(eq("Genre", "Comedy"));

                for (Document recommendation : movieRecommendations) {
                    String jsonString = recommendation.toJson();
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println(json.toString(4));
                }

            } else if (Genre == "Drama") {
                FindIterable<Document> movieRecommendations = recommendations.find(eq("Genre", "Drama"));

                for (Document recommendation : movieRecommendations) {
                    String jsonString = recommendation.toJson();
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println(json.toString(4));
                }

            } else if (Genre == "Family") {
                FindIterable<Document> movieRecommendations = recommendations.find(eq("Genre", "Family"));

                for (Document recommendation : movieRecommendations) {
                    String jsonString = recommendation.toJson();
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println(json.toString(4));
                }

            } else if (Genre == "Horror") {
                FindIterable<Document> movieRecommendations = recommendations.find(eq("Genre", "Horror"));

                for (Document recommendation : movieRecommendations) {
                    String jsonString = recommendation.toJson();
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println(json.toString(4));
                }

            } else if (Genre == "Romance") {
                FindIterable<Document> movieRecommendations = recommendations.find(eq("Genre", "Romance"));

                for (Document recommendation : movieRecommendations) {
                    String jsonString = recommendation.toJson();
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println(json.toString(4));
                }

            } else if (Genre == "Sci-Fi") {
                FindIterable<Document> movieRecommendations = recommendations.find(eq("Genre", "Sci-Fi"));

                for (Document recommendation : movieRecommendations) {
                    String jsonString = recommendation.toJson();
                    JSONObject json = new JSONObject(jsonString);
                    System.out.println(json.toString(4));
                }

            }

            return;
        }
    }

    public static void runSimulation() throws Exception {

        Store store = Store.getInstance();   // Singleton instantiation
        int choice = store.loginScreen();
        int choice1 = 1;
        int choice2 = 1;
        int customerMenuChoice = 1;
        int employeeMenuChoice = 1;
        UserFactory factory = new UserFactory();

        User customer = factory.getUser("Customer");  // will be used if user logs in as customer
        User employee = factory.getUser("Employee");  // will be used if user logs in as employee

        while (choice != 0 && choice1 != 0 && choice2 != 0 && customerMenuChoice != 0 && employeeMenuChoice != 0) {
            if(choice == 1) {  // at some point, redo this choice logic so it doesn't become a huge nested if statement block
                
                choice1 = store.customerMenu();
                customerMenuChoice = store.runCustomer(choice1, customer, store);
            }
            else if(choice == 2) {
                factory.getUser("Employee");
                choice2 = store.employeeMenu();
                employeeMenuChoice = store.runEmployee(choice2, employee, store);
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
        strings.add("Press 5 if you'd like to view the store's current inventory of movies");
        strings.add("Press 6 if you'd like to view the store's current inventory of posters");
        strings.add("");
        strings.add("");
        strings.add("Press 0 if you'd like to exit the store");
        strings.add("");
        DisplayUI(strings);

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
    

        return choice;
    }

    public int runCustomer(int choice, User customer, Store store) throws Exception {
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

            CustomerSearch cSearch = new CustomerSearch(movieTitle, store, customer);

            Movie resultMovie = cSearch.search(movieTitle, store, customer);

            return 1;
        }
        if (choice == 2) {
            List<String> strings1 = new ArrayList<String>();
            strings1.add("");
            strings1.add("Movie Recommendations");
            strings1.add("");
            strings1.add("");
            strings1.add("");
            strings1.add("Press 1 for Action movie recommendations");
            strings1.add("Press 2 for Comedy movie recommendations");
            strings1.add("Press 3 for Drama movie recommendations");
            strings1.add("Press 4 for Family movie recommendations");
            strings1.add("Press 5 for Horror movie recommendations");
            strings1.add("Press 6 for Romance movie recommendations");
            strings1.add("Press 7 for Sci-fi movie recommendations");
            strings1.add("");
            strings1.add("");
            strings1.add("");
            DisplayUI(strings1);
            Scanner scanner = new Scanner(System.in);
            int movieGenre = scanner.nextInt();

            ArrayList<Movie> recommendedMovies = new ArrayList<Movie>();
        
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
                this.GenreSearch("Family");
            }
            if(movieGenre == 5){
                this.GenreSearch("Horror");
            }
            if(movieGenre == 6){
                this.GenreSearch("Romance");
            }
            if(movieGenre == 7){
                this.GenreSearch("Sci-fi");
            }

//            for (int i = 0; i < recommendedMovies.size(); i++) {
//                System.out.println(i+1 + ": " + recommendedMovies.get(i).title);
//                System.out.println("Release Year: " + recommendedMovies.get(i).year);
//                System.out.println("Plot: " + WordUtils.wrap(recommendedMovies.get(i).plot, 90));
//                System.out.println("Director: " + recommendedMovies.get(i).director);
//                System.out.println("Actors: " + recommendedMovies.get(i).actors);
//                System.out.println("Country: " + recommendedMovies.get(i).country);
//                System.out.println("");
//            }

            System.out.println("Press 1 when you are ready to return to the customer menu");
            int userChoice = scanner.nextInt();

            return 1;

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

            List<String> strings2 = new ArrayList<String>();
            strings2.add("");
            strings2.add("Customer Checkout");
            strings2.add("");
            strings2.add("");
            strings2.add("The movies you currently have in your cart are:");
            strings2.add(titles.get(0));
            strings2.add(titles.get(1));
            strings2.add(titles.get(2));
            strings2.add(titles.get(3));
            strings2.add(titles.get(4));
            strings2.add("");
            strings2.add("");
            strings2.add("Press 1 if you'd like confirm checkout");
            strings2.add("Press 2 to go back");
            strings2.add("");
            DisplayUI(strings2);
            Scanner scanner = new Scanner(System.in);
            int confirm = scanner.nextInt();
        
            if(confirm == 1) {
                strings2.clear();
                strings2.add("");
                strings2.add("Thank you!");
                strings2.add("");
                strings2.add("");
                strings2.add("You checked out:");
                strings2.add(titles.get(0));
                strings2.add(titles.get(1));
                strings2.add(titles.get(2));
                strings2.add(titles.get(3));
                strings2.add(titles.get(4));
                strings2.add("");
                strings2.add("");
                strings2.add("Have a good day! We hope to see you again soon.");
                strings2.add("");
                strings2.add("");
                DisplayUI(strings2);
                return 0;  // full exit
            }
            else{
                return 1; // return to menu
            }

        }
        else if (choice == 4) { // events
            int chance = ThreadLocalRandom.current().nextInt(1, 3); // 50/50 chance its an agreement or argument
            if (chance == 1) {  // Agreement

                int chance2 = ThreadLocalRandom.current().nextInt(1,3); // 50/50 chance the agreement has a good result
                
                Agreement agreement = new Agreement();  // regular agreement

                String description = agreement.description;
                String result = agreement.getResult();

                if (chance2 == 1) {  // change it to good result agreement with decorator
                    AgreementGoodResult aGoodResult = new AgreementGoodResult(agreement);
                    description = aGoodResult.description;
                    result = aGoodResult.getResult();
                }

                List<String> strings = new ArrayList<String>();
                strings.add("");
                strings.add(description);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add(result);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                DisplayUI(strings);
                

            }
            else { // Argument
                int chance2 = ThreadLocalRandom.current().nextInt(1,3); // 50/50 chance the agreement has a good result
                
                Argument argument = new Argument();  // regular argument

                String description = argument.description;
                String result = argument.getResult();

                boolean badResult = false;  // keep track of whether its a bad result, because you get kicked out of the store if it is

                if (chance2 == 1) {  // change it to bad result argument with decorator
                    ArgumentBadResult aBadResult = new ArgumentBadResult(argument);
                    description = aBadResult.description;
                    result = aBadResult.getResult();
                    badResult = true;
                }

                List<String> strings = new ArrayList<String>();
                strings.add("");
                strings.add(description);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add(result);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                DisplayUI(strings);

                if (badResult == true) {  // the bad result means you get kicked out of the store
                    return 0;
                }
                

            }

            return 1;
        }
        else if (choice == 5){ // view store's movie inventory
            this.viewMovieInventory();
            return 1;
        }
        else if (choice == 6){ // view store's movie inventory
            this.viewPosterInventory();
            return 1;
        }
        return 0;
    }

    public void viewMovieInventory(){  // retrieve entire movie collection and output each item
        String connectionString = "mongodb+srv://movieDB:csci4448@cluster0.pfn8i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("bock_bluster");
            MongoCollection<Document> shelfCollection = database.getCollection("store_shelf");

            FindIterable<Document> movies = shelfCollection.find();

            for (Document movie : movies) {
                String jsonString = movie.toJson();
                JSONObject json = new JSONObject(jsonString);
                System.out.println(json.toString(4));
            }
        }
    }

    public void viewPosterInventory(){  // retrieve the entire poster collection and output each item
        String connectionString = "mongodb+srv://movieDB:csci4448@cluster0.pfn8i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("bock_bluster");
            MongoCollection<Document> shelfCollection = database.getCollection("poster_shelf");

            FindIterable<Document> posters = shelfCollection.find();

            for (Document poster : posters) {
                String jsonString = poster.toJson();
                JSONObject json = new JSONObject(jsonString);
                System.out.println(json.toString(4));

            }
        }
    }
    
    

    public int employeeMenu() {
        List<String> strings = new ArrayList<String>();
        strings.add("");
        strings.add("Home page for Employee");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("Press 1 if you'd like to search for a movie to order");
        strings.add("Press 2 if you'd like to search for a poster to order");
        strings.add("Press 3 if you'd like to wait around and see what happens in the store");
        strings.add("Press 4 if you'd like to view the store's current inventory of movies");
        strings.add("Press 5 if you'd like to view the store's current inventory of posters");
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

    public int runEmployee(int choice, User employee, Store store) throws Exception {
        if (choice == 1){ //search for a movie to order
            
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
            String movieTitle = scanner.next();
        
            EmployeeSearch eSearch = new EmployeeSearch(movieTitle, store, employee);

            Movie resultMovie = eSearch.search(movieTitle, store, employee);

            return 1;
            }

        /* stocking shelves won't do much, if we make a web page, we could display the movie
            otherwise, we'd just be adding them to another array for no real reason

        if (choice == 2){ //stock the shelves with movies that were ordered
            return 0;
        }
        */

        if (choice == 2){ //youd like to search for posters to order
            List<String> strings = new ArrayList<String>();
            strings.add("");
            strings.add("Poster Search");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("Please enter the title of the movie you want the POSTER for:");
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
            String movieTitle = scanner.next();
        
            PosterSearch eSearch = new PosterSearch(movieTitle, store, employee);

            Movie resultMovie = eSearch.search(movieTitle, store, employee);

            System.out.println("Searched for poster");

            return 1;
            
        }

        else if (choice == 3) { // events
            int chance = ThreadLocalRandom.current().nextInt(1, 3); // 50/50 chance its an agreement or argument
            if (chance == 1) {  // Agreement

                int chance2 = ThreadLocalRandom.current().nextInt(1,3); // 50/50 chance the agreement has a good result
                
                Agreement agreement = new Agreement();  // regular agreement

                String description = agreement.description;
                String result = agreement.getResult();

                if (chance2 == 1) {  // change it to good result agreement with decorator
                    AgreementGoodResult aGoodResult = new AgreementGoodResult(agreement);
                    description = aGoodResult.description;
                    result = aGoodResult.getResult();
                }

                List<String> strings = new ArrayList<String>();
                strings.add("");
                strings.add(description);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add(result);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                DisplayUI(strings);
                

            }
            else { // Argument
                int chance2 = ThreadLocalRandom.current().nextInt(1,3); // 50/50 chance the agreement has a good result
                
                Argument argument = new Argument();  // regular argument

                String description = argument.description;
                String result = argument.getResult();

                boolean badResult = false;  // keep track of whether its a bad result, because you get kicked out of the store if it is

                if (chance2 == 1) {  // change it to bad result argument with decorator
                    ArgumentBadResult aBadResult = new ArgumentBadResult(argument);
                    description = aBadResult.description;
                    result = aBadResult.getResult();
                    badResult = true;
                }

                List<String> strings = new ArrayList<String>();
                strings.add("");
                strings.add(description);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add(result);
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                strings.add("");
                DisplayUI(strings);

                if (badResult == true) {  // the bad result means you get kicked out of the store
                    return 0;
                }
                

            }

            return 1;
        }
        else if (choice == 4){ // view store's movie inventory
            this.viewMovieInventory();
            return 1;
        }
        else if (choice == 5){ // view store's movie inventory
            this.viewPosterInventory();
            return 1;
        }
        return 0;
    }


}

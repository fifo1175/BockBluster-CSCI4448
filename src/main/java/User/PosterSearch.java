package User;

import Store.Movie;
import Store.Poster;
import Store.Store;

// database imports
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

// JSON imports
import org.json.JSONException;

// API imports
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

// Open Url in browser imports
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


import org.apache.commons.text.WordUtils;

import java.util.Scanner;

import User.CustomerSearch;


public class PosterSearch implements SearchStrategy {
    public String movie;
    public Store store;
    public User user;

    public PosterSearch(String movie, Store store, User user) {
        this.movie = movie;
        this.store = store;
        this.user = user;
    }

    public static void posterinsert(Poster poster){ //this is going to take a poster object and insert it into the database
        String connectionString = "mongodb+srv://movieDB:csci4448@cluster0.pfn8i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("bock_bluster");
            MongoCollection<Document> postercollection = database.getCollection("poster_shelf");

            Document posterdoc = new Document();
            posterdoc.append("Title", poster.title)
                    .append("Year", poster.year)
                    .append("URL", poster.URL);

            postercollection.insertOne(posterdoc);
        }
    }
    

    @Override  // get a poster instead of a movie, using factory pattern
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

            Poster resultPoster = null;
            try {
                resultPoster = store.GetPoster(result.getString("imdbID"));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("Title: " + resultPoster.title);
            System.out.println("Release Year: " + resultPoster.year);
            System.out.println("Poster URL: " + resultPoster.URL);
            
            // open poster url in default broser
            // FROM: https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
            String url = resultPoster.URL;

            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("xdg-open " + url);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // add the poster to the store's poster inventor
            store.postersInStock.add(resultPoster);

            // add the poster to the database
            this.posterinsert(resultPoster);
           
            return null;
        }
    }
}


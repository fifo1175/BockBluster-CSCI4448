package User;

import Store.Movie;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

// used tutorial: https://www.mongodb.com/developer/quickstart/java-setup-crud-operations/

public class movieInsert {

    public movieInsert(){};

    public static void insert(Movie movie) {   // method to insert a movie into the store_shelf collection in the database

        String connectionString = "mongodb+srv://movieDB:csci4448@cluster0.pfn8i.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("bock_bluster");
            MongoCollection<Document> shelfCollection = database.getCollection("store_shelf");

            Document movieDocument = new Document();

            movieDocument.append("title", movie.title)
                    .append("imdbID", movie.imdbID)
                    .append("year", movie.year)
                    .append("filmRating", movie.filmRating)
                    .append("runtime", movie.runtime)
                    .append("imdbRating", movie.imdbRating)
                    .append("genre", movie.genre)
                    .append("plot", movie.plot)
                    .append("director", movie.director)
                    .append("actors", movie.actors)
                    .append("country", movie.country);

            shelfCollection.insertOne(movieDocument);
        }


    }



}

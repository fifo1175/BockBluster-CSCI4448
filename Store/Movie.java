package Store;

public class Movie {
    public String title;
    public String imdbID;
    public String year;
    public String filmRating;
    public String runtime;
    public String imdbRating;
    public String genre;
    public String plot;
    public String director;
    public String actors;
    public String country;
    

    public Movie() {
        this.title = "";
        this.imdbID = "";
        this.year = "";
        this.filmRating = "";
        this.runtime = "";
        this.imdbRating = "";
        this.genre = "";
        this.plot = "";
        this.director = "";
        this.actors = "";
        this.country = "";
    }

    public Movie(String title, String imdbID, String year, String filmRating, String runtime, String imdbRating,
                 String genre, String plot, String director, String actors, String country) {
        this.title = title;
        this.imdbID = imdbID;
        this.year = year;
        this.filmRating = filmRating;
        this.runtime = runtime;
        this.imdbRating = imdbRating;
        this.genre = genre;
        this.plot = plot;
        this.director = director;
        this.actors = actors;
        this.country = country;
    }
}

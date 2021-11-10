package User;

import Store.Movie;

public interface SearchStrategy {
    public Movie search(String title);
}


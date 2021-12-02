package User;

import Store.Movie;
import Store.Store;

public interface SearchStrategy {
    public Movie search(String title, Store store, User user);
}


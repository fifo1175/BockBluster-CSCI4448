package User;

import java.util.ArrayList;

import Store.Movie;
import Store.Poster;

public interface User {
    public void setSearchStrategy(SearchStrategy strat) ;
}

class Employee implements User {

    public SearchStrategy searchStrat;

    public Employee() {}

    // once a movie has been searched for (through EmployeeSearch search() method), the movie returned can then be used in this method to actually order it and add it to the store
    public String orderMovie(Movie movie) {
        return null;
    }

    // same idea as orderMovie, but with a poster object
    public String orderPoster(Poster poster) {
        return null;
    }

    public void setSearchStrategy(SearchStrategy strat) {
        this.searchStrat = strat;
    }
    
}

class Customer implements User {
    public SearchStrategy searchStrat;

    public Customer() {}

    // returns a string so when it's called, we can grab that confirmation string and put it into the output
    public String addToCart(Movie movie) {
        return null;
    }

    public String checkout(ArrayList<Movie> cart) {
        return null;
    }

    public void setSearchStrategy(SearchStrategy strat) {
        this.searchStrat = strat;
    }
}

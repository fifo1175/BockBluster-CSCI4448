package Store;

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

    

    public void DisplayEmployeeUI() { // may not even need this, can just use 1 UI function and change the strings that go into it
        
    }

    public Movie TitleSearch(String title){  // search API for movie by title
        return null;
    }

    public ArrayList<Movie> GenreSearch(String Genre) {  // search API for movies by genre, return 5 of that genre
        return null;
    }

    public static void runSimulation() {

        Store store = new Store();
        int choice = store.loginScreen();
        int choice1 = 1;
        int choice2 = 1;
        int customerMenuChoice = 1;
        UserFactory factory = new UserFactory();

        while (choice != 0 && choice1 != 0 && choice2 != 0 && customerMenuChoice != 0) {
            if(choice == 1) {  // at some point, redo this choice logic so it doesn't become a huge nested if statement block
                User customer = factory.getUser("Customer");
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

    public int runCustomer(int choice, User customer) {
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
            String movieTitle = scanner.next();
        
            this.TitleSearch(movieTitle);
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

            for (int i = 0; i < 5; i++) {
                if(customer.cart.get(i).title != "empty") {
                    titles.add(customer.cart.get(i).title);
                }
                else {
                    titles.add("");
                }
                
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

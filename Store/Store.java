package Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public Movie APISearch(String title){
        return null;
    }

    public static void runSimulation() {

        Store store = new Store();
        int choice = store.loginScreen();

        UserFactory factory = new UserFactory();

        if(choice == 1) {
            factory.getUser("Customer");
            int choice1 = store.customerMenu();
        }
        else if(choice == 2) {
            factory.getUser("Employee");
        }
        else {
            return;
        }

        //store.DisplayCustomerUI(strings);
    }

    public int loginScreen() {

        List<String> strings = new ArrayList<String>();
        strings.add("");
        strings.add("Welcome to BockBluster Movie Rental and Recommendations!");
        strings.add("");
        strings.add("Use the number keys to navigate the menu");
        strings.add("");
        strings.add("Press 1 if you're a customer");  // length 28, half 14, start 35
        strings.add("Press 2 if you're an employee"); // length 29, half 14, start 35, should be 1 less space since the length is 1 longer
        strings.add("Press 3 if you're just passing by!");
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
        strings.add("Press 1 if you'd like to search for a movie");  // length 28, half 14, start 35
        strings.add("Press 2 if you'd like to get some movie recommendations"); // length 29, half 14, start 35, should be 1 less space since the length is 1 longer
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



    
}

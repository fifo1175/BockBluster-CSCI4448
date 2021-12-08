package Events;

public class Agreement extends Events { 

    public Agreement() {
        description = "You and an employee agree that the best sci-fi movie of the year is Dune.";
        occurences = 0;
        result = "The employee gives you a small discount on your movie rental.";
    }

    public String getResult() {
        return this.result;
    }
}


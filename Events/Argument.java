package Events;

public class Argument extends Events {

    public Argument() {       
        description = "Oh no! An argument breaks out between you and a customer about if Batman or Spiderman is better.";
        occurences = 0;
        result = "Luckily, you and the customer resolved the argument well.";
    }

    public String getResult() {
        return this.result;
    }
}

package Events;

// Argument event with decorator to change the result

public class ArgumentBadResult extends EventsDecorator{
    public Events event;
    public String description;

    public ArgumentBadResult(Events event) {
        this.event = event;
        this.description = event.description;
    }

    @Override
    public String getResult() {  // modifying the event's result to be bad
        event.result = "Bad news!  Looks like an employee is kicking you out of the store!";
        return event.result;
    }
    
}

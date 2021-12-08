package Events;

// Agreement event with Decorator to change the result

public class AgreementGoodResult extends EventsDecorator{
    public Events event;
    public String description;

    public AgreementGoodResult(Events event){
        this.event = event;
        this.description = event.description;
    }

    @Override
    public String getResult() {  // modifying the event's result to be good
        event.result = "Great news!  The employee says you can rent 3 movies for free at your next checkout!";
        return event.result;
    }
    
}

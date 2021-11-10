package Events;

public class EventsDecorator extends Events {
    public Events event;

    public String storeEvent(){
        return null;
    }
}

class Argument extends EventsDecorator {  // maybe start a fight in the argument event?

}

class Agreement extends EventsDecorator{  // agreement with employee about best movie in some genre leads to getting a free movie of your choice maybe?
                                          // could have it say "Bob the cashier comes up to you and asks you about your favorite Action movie, what do you say:" and then some options come up and if you choose the right one, you agree with bob

}

package Events;

import User.User;

// Decorator class that inherits from base class
// getDescription will be modified using the decorator pattern

public abstract class EventsDecorator extends Events {
    public abstract String getResult();

}

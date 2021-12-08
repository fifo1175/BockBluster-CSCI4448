package Events;

import java.util.ArrayList;

import User.User;

public abstract class Events {
    public String description;  // what happens in the event
    public String result;  // the result of the event
    User user;
    public int occurences;

    public abstract String getResult();

}
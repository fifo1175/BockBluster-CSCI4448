package User;

public class UserFactory {
    
    public UserFactory(){}

    public User getUser(String userType) {
        if(userType == null) {
            return null;
        }
        if(userType == "Customer") {
            return new Customer();
        }
        if(userType == "Employee") {
            return new Employee();
        }
        return null;
    }
}

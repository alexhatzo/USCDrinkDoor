import java.util.*;


public class Buyer extends User{
    Order currentOrder;
    List<Order> pastOrders;
    int dailyCaffeine;
    String location;
    String name;
    String birthday;
    int userID;


    public Buyer(String name, String birthday, String location, int userID) {
        this.name = name;
        this.birthday = birthday;
        this.location = location;
        this.userID = userID;
    }

    //getters
    public String getName() {}

    public String getBirthday() {}

    public Order getCurrentOrder() {}

    public String getPastOrders() {}

    public String getLocation() {}

    public int getUserID() {}

    //setters
    public void setName() {}

    public void setBirthday() {}

    public void setLocation() {}

    //add order
    public void addOrder() {}

    //login and logout
    public void login() {}

    public void logout() {}



}
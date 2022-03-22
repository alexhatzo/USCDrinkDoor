import java.util.*;


public class Buyer extends User{
    Order currentOrder;
    List<Order> pastOrders;
    int dailyCaffeine;
    Location location;
    String name;
    String birthday;
    int userID;


    public Buyer(String name, String birthday, Location location, int userID) {
        this.name = name;
        this.birthday = birthday;
        this.location = location;
        this.userID = userID;
    }

    //getters
    public String getName() { return name;}

    public String getBirthday() { return  birthday;}

    //fix
    public Order getCurrentOrder() { return null;}

    //fix
    public String getPastOrders() { return null;}

    public Location getLocation() { return location;}

    public int getUserID() { return userID;}

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
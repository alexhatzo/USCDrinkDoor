import java.util.*;

public class Item {
    String itemName;
    String description;
    int itemID;
    int price;
    int caffeineMg;
    int sellerID;

    public Item(String itemName, String description, int itemID, int price, int caffeineMg, int sellerID) {
        this.itemName = itemName;
        this.description = description;
        this.itemID = itemID;
        this.price = price;
        this.caffeineMg = caffeineMg;
        this.sellerID = sellerID;
    }

    //getters
    public String getName() { return itemName; }

    public String getDescription() { return description; }

    public int getItemID() { return itemID; }

    public int getPrice() { return price; }

    public int getCaffeine() { return caffeineMg; }

    public int getSellerID() { return sellerID; }

    //setters
    public void changeName() {}

    public void changeDescription() {}

    public void changePrice() {}




}
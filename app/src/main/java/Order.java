import com.example.uscdrinkdoor.Item;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public String Status; // "Active" or "Past"
    private int OrderID;
    private int UserID;
    private int SellerID;
    private List<Item> Items;

    public Order(int userID, int sellerID, List<Item> items){
        UserID = userID;
        SellerID = sellerID;
        Items = new ArrayList<Item>();
        Items.addAll(items);
    }

    public void Display_Order(){}

    public void Update_Status(){}

    public int Get_Order_ID(){ return this.OrderID; }

    public int Get_User_ID(){ return UserID; }

    public int Get_Seller_ID(){ return SellerID; }

    public List<Item> Get_Items(){
        return new ArrayList<>();
    }


}
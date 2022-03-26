import com.example.uscdrinkdoor.Item;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private int CartID;
    private int UserID;
    private int SellerID;
    private List<Item> items;

    public ShoppingCart(int userID, int sellerID, int cartID){
        UserID = userID;
        SellerID = sellerID;
        CartID = cartID;
        items = new ArrayList<Item>();
    }

    public void Add_Item(Item i){}

    public void Remove_Item(Item i){}

    public void Display_Cart(){}

    public int Get_User_ID(){ return UserID; }

    public int Get_Seller_ID(){ return SellerID; }

    public int Get_Cart_ID(){ return CartID; }

    public List<Item> Get_Items(){
        return new ArrayList<>();
    }

    public void Submit_Order(){
        // Create order object here
        Order order = new Order(this.UserID, this.SellerID, this.items);
        // Pass order object to seller


    }
}
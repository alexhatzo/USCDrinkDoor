public class ShoppingCart {

    private int CartID;
    private int UserID;
    private int SellerID;
    private List<Item> Items;

    public ShoppingCart(int userID, int sellerID, int cartID){
        UserID = userID;
        SellerID = sellerID;
        CartID = cartID;
        Items = new ArrayList<Item>();
    }

    public void Add_Item(Item i){}

    public void Remove_Item(Item i){}

    public void Display_Cart(){}

    public int Get_User_ID(){}

    public int Get_Seller_ID(){}

    public int Get_Cart_ID(){}

    public List<Item> Get_Items(){}

    public void Submit_Order(){
        // Create order object here
        // Pass order object to seller

    }
}
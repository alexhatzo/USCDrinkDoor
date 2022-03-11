public class Order {

    public String Status; // "Active" or "Past"
    private int OrderID;
    private int UserID;
    private int SellerID;
    private List<Item> Items;

    public Order(int orderID, int userID, int sellerID, List<Item> items){
        OrderID = orderID;
        UserID = userID;
        SellerID = sellerID;
        Items = new ArrayList<Item>();
        Items.addAll(items);
    }

    public void Display_Order(){}

    public void Update_Status(){}

    public int Get_Order_ID(){}

    public int Get_User_ID(){}

    public int Get_Seller_ID(){}

    public List<Item> Get_Items(){}


}
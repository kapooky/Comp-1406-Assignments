import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by tariq on 15/02/17.
 */
public class ShoppingListView extends Pane{
    private Shopper model;

    //Third colum
    private Label productsLabel;
    public ListView productsList;                //CHANGED TO PUBLIC REPLACE W/ PRIVATE
    private Button buyButton;

    private Label shoppingCartLabel;
    public ListView shoppingcartList; //CHANGE BACK TO PUBLIC W/ GETTER
    private Button returnButton;

    private Label contentsLabel;
    public javafx.scene.control.ListView<String> CheckoutList; //CHANGE BACK TO PUBLIC W/ GETTER
    private Button checkoutButton;
    private TextField priceTextField;
    private  Label totalPriceLabel;

    public Button getBuyButton() { return buyButton; }

    public GroceryItem[] getProducts(){
        return products;
    }

    public Button getReturnButton(){
        return returnButton;
    }

    public TextField getPriceTextField(){
        return priceTextField;
    }
    public Button getCheckoutButton(){
        return checkoutButton;
    }





    private static final GroceryItem[] products = {
            new FreezerItem("Smart-Ones Frozen Entrees", 1.99f, 0.311f),
            new GroceryItem("SnackPack Pudding", 0.99f, 0.396f),
            new FreezerItem("Breyers Chocolate Icecream",2.99f,2.27f),
            new GroceryItem("Nabob Coffee", 3.99f, 0.326f),
            new GroceryItem("Gold Seal Salmon", 1.99f, 0.213f),
            new GroceryItem("Ocean Spray Cranberry Cocktail",2.99f,2.26f),
            new GroceryItem("Heinz Beans Original", 0.79f, 0.477f),
            new RefrigeratorItem("Lean Ground Beef", 4.94f, 0.75f),
            new FreezerItem("5-Alive Frozen Juice",0.75f,0.426f),
            new GroceryItem("Coca-Cola 12-pack", 3.49f, 5.112f),
            new GroceryItem("Toilet Paper - 48 pack", 40.96f, 10.89f),
            new RefrigeratorItem("2L Sealtest Milk",2.99f,2.06f),
            new RefrigeratorItem("Extra-Large Eggs",1.79f,0.77f),
            new RefrigeratorItem("Yoplait Yogurt 6-pack",4.74f,1.02f),
            new FreezerItem("Mega-Sized Chocolate Icecream",67.93f,15.03f)};


    public ShoppingListView(Shopper m){
    model = m;			// Store the model for access later
        // these variables will help to increment the data to right position/left position
        int x = 12;
        int y = 10;

    //set the first column
    productsLabel = new Label("Products");
    productsLabel.relocate(10+x,10);
    productsLabel.setPrefSize(200,25);

    productsList = new ListView<GroceryItem[]>();
    productsList.relocate(10+x,45+y);
    productsList.setPrefSize(200,300);

    productsList.setItems(FXCollections.observableArrayList(products));
    //SET LATER

    buyButton = new Button("Buy");
    buyButton.relocate(10+x,355+y);
    buyButton.setPrefSize(200,25);




            // Set the second column


    shoppingCartLabel = new Label("Shopping Cart");
    shoppingCartLabel.relocate(220 + x,10);
    shoppingCartLabel.setPrefSize(200,25);

    shoppingcartList = new javafx.scene.control.ListView<String[]>();
    shoppingcartList.relocate(220 + x,45+y);
    shoppingcartList.setPrefSize(200,300);

    returnButton = new Button("Return");
    returnButton.relocate(220 + x,355+y);
    returnButton.setPrefSize(200,25);

    //Set the third column
    contentsLabel = new Label("Contents");
    contentsLabel.relocate(430+ x,10);
    contentsLabel.setPrefSize(200,25);

    CheckoutList = new javafx.scene.control.ListView<String>();
    CheckoutList.relocate(430 + x,45+y);
    CheckoutList.setPrefSize(300,300);

    checkoutButton = new Button("Checkout");
    checkoutButton.relocate(430 + x,355+y);
    checkoutButton.setPrefSize(120,25);

    totalPriceLabel = new Label("Total Price:");
    totalPriceLabel.relocate(565 + x,355 + y);
    totalPriceLabel.setPrefSize(100,25);

    priceTextField = new TextField("$0.00");
    priceTextField.relocate(630 + x,355 +y);
    priceTextField.setPrefSize(100,25);
    priceTextField.setAlignment(Pos.CENTER_RIGHT);
    priceTextField.setDisable(true);


    getChildren().addAll(productsLabel,productsList,buyButton,shoppingCartLabel,
            shoppingcartList,returnButton,contentsLabel,CheckoutList, checkoutButton
    ,priceTextField,totalPriceLabel);
    update();



}
public void update() {

    //Update the Content list in real time
    String[] exactArray = new String[model.getNumItems()];
    GroceryItem Itemtypecast;
    GroceryBag bagTypeCast;
    for (int i = 0; i < model.getNumItems(); i++) {
        if (model.getCart()[i] instanceof GroceryItem) {
            Itemtypecast = (GroceryItem) (model.getCart()[i]);         //Entire block of code is dedicated towards updating the contentsArray
            exactArray[i] = Itemtypecast.getName();
        } else {
            bagTypeCast = (GroceryBag) (model.getCart()[i]);
            exactArray[i] = bagTypeCast.toString();
        }
    }

    shoppingcartList.setItems(FXCollections.observableArrayList(exactArray));  //SET the observable to exact array


// Buy button is disabled if there is no selected Index in products list
    //
    if (!checkoutButton.getText().equals("Restart Shopping")) {                  // if buy buttonn's text doesn't equal "restart shopping" then do this
        buyButton.setDisable(productsList.getSelectionModel().getSelectedIndex() < 0);

        // if the shooping cart selection is enabled, the return button is enabled
        returnButton.setDisable(!(shoppingcartList.getSelectionModel().getSelectedIndex() >= 0));
    }

    //if there is no items in the shopping cart, then checkout button should be disabled
    checkoutButton.setDisable(model.getNumItems() == 0);


}



}

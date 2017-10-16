/**
 * Created by tariq on 15/02/17.
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;
public class ShoppingListApp extends Application{
    private Shopper model;
    private ShoppingListView view;

    public void start(Stage primaryStage){
        model = new Shopper();
        view= new ShoppingListView(model);

        primaryStage.setTitle("Shopping List Application");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(view, 750,405));
        primaryStage.show();

        //Plug in the event handlers
        view.productsList.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { handleListSelection();
            }
        });


        view.shoppingcartList.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { handleContentShoppingListSelection();
            }
        });

        view.getBuyButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { handleBuybuttonPress(); }
        });

        view.getReturnButton().setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) { handleReturnButtonPress(); }
        });

        view.getCheckoutButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if(view.getCheckoutButton().getText().equals("Restart Shopping")){
                    handleRestartButtonPress();
                    view.update();
                }
                else{
                    handleCheckoutButtonPress();
                }

            }
        });


    }

    public static void main(String[] args){
        launch(args);
    }
    float price = 0;  //IMPORTANT: this lives outside the methods,
    private void handleListSelection(){
       view.update();
    }

    private void handleBuybuttonPress(){
        int index = view.productsList.getSelectionModel().getSelectedIndex();


        model.addItem(view.getProducts()[index]);

        //Also sum up the total Price!

        price += view.getProducts()[index].getPrice();
        view.getPriceTextField().setText(String.format("%s%.2f","$",price)); //redudnant stringformatting

        view.productsList.getSelectionModel().select(-1);
        view.update();
    }

    private void handleReturnButtonPress(){
        int index = view.shoppingcartList.getSelectionModel().getSelectedIndex();

        if(index>=0 && index < model.getNumItems()) {
            model.removeItem(model.getCart()[index]);

            price -= model.getCart()[index].getPrice();
            if(price<0)
                price = 0;
            view.getPriceTextField().setText(String.format("%s%.2f","$",price));

        }
        view.productsList.getSelectionModel().select(-1);
        view.update();

    }

    private void handleCheckoutButtonPress(){


        //change the text of the button to somthing else
        view.getCheckoutButton().setText("Restart Shopping");
        view.getReturnButton().setDisable(true);
        view.getBuyButton().setDisable(true); // why isnt this disabling??


        // Print the monthly shit
        GroceryItem temp;
        for(int i =0; i<model.getNumItems();i++) {
              temp = (GroceryItem)(model.getCart()[i]);
            System.out.println((String.format("%-40s%15.2f",temp.getName(),temp.getPrice())));
        }
        System.out.println("-------------------------------------------------------");
        System.out.println(String.format("%-40s%15.2f","TOTAL",price));
        model.packBags();
        view.update();

    }

    private void handleContentShoppingListSelection(){


        int index = view.shoppingcartList.getSelectionModel().getSelectedIndex();

        if (view.getCheckoutButton().getText().equals("Restart Shopping") ) {
            if (index >= 0 && index < model.getNumItems()) {
                GroceryBag typecastGroceryBag;
                if (model.getCart()[index] instanceof GroceryBag) {
                    typecastGroceryBag = (GroceryBag) model.getCart()[index];
                  //  GroceryItem[] temp = view.getProducts();
                     view.CheckoutList.setItems(FXCollections.observableArrayList(typecastGroceryBag.getContents())); //error
                    view.update();
                }
            }
        }
        view.update();


    }

    private void handleRestartButtonPress(){
        view.CheckoutList.setItems(null);

        //set the buttons back up again, cuz we're restarting the application
       // view.getBuyButton().setDisable(false);
       // view.getReturnButton().setDisable(false);

        //Print out the price stuff here


        // remove everything from the shopping cart use a damm forlooop
        for(int i =0; i<model.getNumItems()+50;i++){
            model.removeItem(model.getCart()[0]);
           /// view.update();
        }
        //set the price to $0.00
        price = 0.00f;
        view.getPriceTextField().setText(String.format("%s%.2f","$",price));
        view.getCheckoutButton().setText("Checkout");
        view.update();





    }


}

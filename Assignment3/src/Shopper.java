import java.awt.*;

/**
 * Created by tariq on 26/01/17.
 */
public class Shopper {
    private   static byte MAX_CART_ITEMS = 100;
    private GroceryItem[] cart;
    private  byte itemCount;


    //getters

    public GroceryItem[] getCart() {
        return cart;
    }

    public byte getNumItems() {
        return itemCount;
    }

    //Constructor
    public Shopper() {
        itemCount = 0;
        cart = new GroceryItem[MAX_CART_ITEMS];
    }

    //toString
    public String toString() {
        return "Shopper with shopping cart containing " + getNumItems() + " items.";
    }

    //Various  methods

    public void addItem(GroceryItem input) {
        if (itemCount < MAX_CART_ITEMS) {
            cart[itemCount] = input;
            itemCount++;
        }
    }

    public void removeItem(GroceryItem item) {
        for (int i = 0; i < itemCount; i++) {
            if (getCart()[i] == item) {
                while (i < itemCount) {
                    this.cart[i] = this.getCart()[i + 1];
                    i++;
                }
                this.cart[itemCount - 1] = null;

                itemCount--;
                break;

            }
        }
    }

    public GroceryBag[] packBags() {
        int cartCounter = 0;
        int bagCounter = 0;
        GroceryBag[] plasticBags = new GroceryBag[100];
        plasticBags[0] = new GroceryBag();

    while(cartCounter < itemCount) {
        if (cart[cartCounter].getWeight() >= plasticBags[0].getMax_Weight()) {
            //leave the item in the cart

            cartCounter++;

        } else {
            //if weight of the current bag is > than the max Weight
            if (plasticBags[bagCounter].getWeight() + this.cart[cartCounter].getWeight() > plasticBags[0].getMax_Weight()) { // (this.cart[cartCounter].getWeight() < plasticBags[0].getMax_Weight())
                bagCounter++;
                plasticBags[bagCounter] = new GroceryBag();

            } else {
                plasticBags[bagCounter].addItem(this.cart[cartCounter]); //add item to bag

                removeItem(cart[cartCounter]);//remove item from the cart


            }
        }
    }

        GroceryBag[] RealBags = new GroceryBag[bagCounter +1 ];
        for(int x = 0; x < RealBags.length;x++) {
            RealBags[x] = new GroceryBag();
            for (int n = 0; n < plasticBags[x].getNumItems();n++) {
                RealBags[x].addItem(plasticBags[x].getItems()[n]);
            }
        }
        return RealBags;

}



}























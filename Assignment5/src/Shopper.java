/**
 * Created by tariq on 2/13/2017.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javax.swing.text.html.ListView;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.annotation.Repeatable;


public class Shopper {
    public static final int MAX_CART_ITEMS = 100;  // max # items allowed

    private Carryable[] cart;       // items to be purchased
    private static int numItems;   // #items to be purchased

    public Shopper() {
        cart = new Carryable[MAX_CART_ITEMS];
        numItems = 0;
    }

    public Carryable[] getCart() {
        return cart;
    }

    public int getNumItems() {
        return numItems;
    }

    public String toString() {
        return "Shopper with shopping cart containing " + numItems + " items";
    }

    // Return the total cost of the items in the cart
    public float totalCost() {
        float total = 0;
        for (int i = 0; i < numItems; i++) {
            total += cart[i].getPrice();
        }
        return total;
    }

    // Add an item to the shopper's shopping cart
    public void addItem(Carryable g) {
        if (numItems < MAX_CART_ITEMS)
            cart[numItems++] = g;
    }

    // Removes the given item from the shopping cart
    public void removeItem(Carryable g) {
        for (int i = 0; i < numItems; i++) {
            if (cart[i] == g) {
                cart[i] = cart[numItems - 1];
                numItems -= 1;
                return;
            }
        }
    }

    // Go through the shopping cart and pack all packable items into bags
    public void packBags() {
        GroceryBag[] packedBags = new GroceryBag[numItems];
        int bagCount = 0;

        GroceryBag currentBag = new GroceryBag();
        for (int i = 0; i < numItems; i++) {
            GroceryItem item = (GroceryItem) cart[i];
            if (item.getWeight() <= GroceryBag.MAX_WEIGHT) {
                if (!currentBag.canHold(item)) {
                    packedBags[bagCount++] = currentBag;
                    currentBag = new GroceryBag();
                }
                currentBag.addItem(item);
                removeItem(item);
                i--;
            }
        }
        // Check this in case there were no bagged items
        if (currentBag.getWeight() > 0)
            packedBags[bagCount++] = currentBag;

        // Now create a new bag array which is just the right size
        GroceryBag[] result = new GroceryBag[bagCount];
        for (int i = 0; i < bagCount; i++)
            result[i] = packedBags[i];

        //Now put the grocerybags back into the cart
        for (int i = 0; i < bagCount; i++) {
            addItem(result[i]);
        }
    }

    public String displayCartContents() {
        String returnString = "";
        for (int i = 0; i < numItems; i++) {
            returnString += "\n" + getCart()[i].toString();

            if (!getCart()[i].getContents().isEmpty()) {
                GroceryBag temp = (GroceryBag) getCart()[i];
                for (int x = 0; x < temp.getNumItems(); x++) {
                    returnString += "\n   " + temp.getItems()[x];
                }


            }
        }
        return returnString;
    }

    public PerishableItem[] removePerishables() {
        int perishcounter = 0;
        PerishableItem[] tempArray = new PerishableItem[getNumItems() + 40];

        for (int i = 0; i < numItems; i++) {
            if (getCart()[i] instanceof PerishableItem) {
                tempArray[perishcounter] = (PerishableItem) getCart()[i];
                perishcounter++;
                removeItem(getCart()[i]);


                //reduce bagweught or numcount?

            } else if (getCart()[i] instanceof GroceryBag) {
                GroceryBag temp = (GroceryBag) getCart()[i];
                for (int j = 0; j < temp.getNumItems(); j++) {
                    if (temp.getItems()[j] instanceof PerishableItem) {
                        tempArray[perishcounter] = (PerishableItem) temp.getItems()[j];
                        perishcounter++;

                        temp.removeItem(temp.getItems()[j]);
                        j--;
                    }

                }
            } else {
                //donothing
            }
        }

        PerishableItem[] real = new PerishableItem[perishcounter];

        for (int n = 0; n < perishcounter; n++) {
            real[n] = tempArray[n];
        }

        return real;
    }

    public float computeFreezerItemCost() {
        float returnThis = 0;
        for (int i = 0; i < getNumItems(); i++) {
            if(getCart()[i] instanceof FreezerItem) {
                FreezerItem temp = (FreezerItem) (getCart()[i]);
                returnThis += temp.getPrice();
            }

        }
        return returnThis;
    }


    public float computeTotalCost() {
        float returnhis = 0;
        for (int i = 0; i < getNumItems(); i++) {
            Carryable temp = getCart()[i];
            returnhis += temp.getPrice();
        }
        return returnhis;
    }
}

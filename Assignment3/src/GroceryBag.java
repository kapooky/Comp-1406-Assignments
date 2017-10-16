/**
 * Created by tariq on 26/01/17.
 */
public class GroceryBag {
    private static double MAX_WEIGHT = 5.00;
    private static int MAX_ITEMS = 25;
    private GroceryItem[] items;

    private int numItems;
    private float Bagweight;

    //DECLARE CONSTRUCTORS
    public GroceryBag() {
        items = new GroceryItem[MAX_ITEMS];
        numItems = 0;
        Bagweight = 0.0f;
    }

    public GroceryItem[] getItems() {
        return this.items;
    }

    public int getNumItems() {
        return this.numItems;

    }

    public float getWeight() {
        return this.Bagweight;
    }

    public double getMax_Weight() {
        return MAX_WEIGHT;
    }
    //created an external setter method

    public void setItems(GroceryItem[] items) {
        this.items = items;

    }


    public void addItem(GroceryItem inputItem) {
        this.Bagweight += inputItem.getWeight();
        if (this.getWeight() <= MAX_WEIGHT && (this.numItems < MAX_ITEMS)) {
            this.items[numItems] = inputItem;
            this.numItems++;
        } else {
            System.out.println("The item could not be added, bag too full");
            this.Bagweight -= inputItem.getWeight();
        }


    }

    public void removeItem(GroceryItem item) {
        for (int i = 0; i < this.numItems; i++) {
            if (this.items[i] == item) {

                while (i < numItems - 1) {
                    this.items[i] = getItems()[i + 1];
                    i++;
                }
            }
            items[numItems - 1] = null;
            break;
        }
    }

    public GroceryItem heaviestItem() {
        GroceryItem HeaviestItem = null;
        float maximum = 0.0f;
        for (int i = 0; i < numItems; i++) {
            if (this.items[i].getWeight() > maximum) {
                maximum = this.items[i].getWeight();
                HeaviestItem = this.items[i];
            }
        }
        return HeaviestItem;
    }

    public boolean has(GroceryItem item) {
        for (int i = 0; i < numItems; i++) {
            if (this.items[i] == item) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (this.numItems > 0)
            return "a " + getWeight() + "kg with " + getNumItems() + " items";
        return "An Empty Grocery bag";
    }

    public GroceryItem[] unpackPerishables() {
        int counter = 0;
        for(int i = 0; i < getNumItems();i++){
            if (getItems()[i].isPerishable()){
                counter++;
            }
        }
        GroceryItem[] temp = new GroceryItem[getNumItems()-counter];
        GroceryItem[] perish = new GroceryItem[counter];
        int j = 0;
        int x =0;
        for (int i = 0; i < getNumItems(); i++) {
            if (getItems()[i].isPerishable()) {
                perish[j] = getItems()[i];
                j++;
                this.Bagweight -= getItems()[i].getWeight();

            } else {
                temp[x] = getItems()[i];
                x++;
            }
        }

         items = new GroceryItem[temp.length];

        for(int i =0; i < getNumItems() - counter;i++){
            this.items[i] = temp[i];
        }

        numItems = numItems - counter;


        return perish;
    }
}

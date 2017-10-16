/**
 * Created by tariq on 2/13/2017.
 */
public class GroceryItem implements Carryable {
    private String name;
    private float price;
    private float weight;


    public GroceryItem() {
        name = "?";
        price = 0;
        weight = 0;

    }

    public GroceryItem(String n, float p, float w) {
        name = n;
        price = p;
        weight = w;

    }

    public String getName() {
        return name;
    }


    public float getWeight() {
        return weight;
    }


    //GroceryItem.Carryable Interfaces
    @Override
    public String getContents() {
        return "";
    }

    @Override
    public String getDescription() {
        return this.name;
    }

    @Override
    public float getPrice() {
        return price;
    }


    public String toString() {
        return name + " weighing " + weight + "kg with price $" + price;
    }

}


/**
 * Created by tariq on 26/01/17.
 */
public class GroceryItem {
    private String name;
    private float price;
    private float weight;
    private boolean perishable; //true indicates item needs to be frozen/refregitated

    public GroceryItem(String enterName,float enterPrice,float enterWeight){
        name = enterName;
        price = enterPrice;
        weight = enterWeight;
        perishable = false;

    }

    public GroceryItem(String enterName,float enterPrice, float enterWeight, boolean enterCondition ){
        name = enterName;
        price = enterPrice;
        weight = enterWeight;
        perishable = enterCondition;

    }
    public String getName(){
        return this.name;
    }
    public float getPrice(){
        return this.price;
    }
    public float getWeight() {
        return this.weight;
    }
    public boolean isPerishable(){
        return this.perishable;
    }



    public String toString(){
        return this.name + " weighing " + this.weight + "kg" + " with price $" + this.price;
    }
}

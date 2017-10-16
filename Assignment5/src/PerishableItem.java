/**
 * Created by tariqazmat on 2/9/2017.
 */
public abstract class PerishableItem extends GroceryItem{

    public PerishableItem(String Name,float p,float w){
        super(Name,p,w);

    }

    public String toString(){
        return super.toString()+ "(Perishable)";
    }




}

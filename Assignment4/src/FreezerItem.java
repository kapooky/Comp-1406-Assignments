/**
 * Created by tariqazmat on 2/9/2017.
 */

//Pertaining to the userinterface
public  class FreezerItem extends PerishableItem {
    public FreezerItem(String Name,float p,float w){
        super(Name,p,w);

    }

    public String toString(){
        return super.toString() + "[keep Frozen]";
    }


}
//concrete classes
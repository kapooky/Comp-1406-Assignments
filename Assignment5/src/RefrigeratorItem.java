/**
 * Created by tariqazmat on 2/9/2017.
 */
public class RefrigeratorItem extends PerishableItem {
    public RefrigeratorItem(String Name, float p, float w) {
        super(Name, p, w);

    }

    public String toString() {
        return super.toString() + "[Keep Refrigerated]";

    }
}

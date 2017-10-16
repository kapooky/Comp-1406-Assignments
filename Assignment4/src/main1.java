/**
 * Created by tariq on 12/02/17.
 */
public class main1 {
    public static void main(String[] args) {
        GroceryItem g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15;
        g1 = new FreezerItem("Smart-Ones Frozen Entrees", 1.99f, 0.311f);
        g2 = new GroceryItem("SnackPack Pudding", 0.99f, 0.396f);
        g3 = new FreezerItem("Breyers Chocolate Icecream", 2.99f, 2.27f);
        g4 = new GroceryItem("Nabob Coffee", 3.99f, 0.326f);
        g5 = new GroceryItem("Gold Seal Salmon", 1.99f, 0.213f);
        g6 = new GroceryItem("Ocean Spray Cranberry Cocktail", 2.99f, 2.26f);
        g7 = new GroceryItem("Heinz Beans Original", 0.79f, 0.477f);
        g8 = new RefrigeratorItem("Lean Ground Beef", 4.94f, 0.75f);
        g9 = new FreezerItem("5-Alive Frozen Juice", 0.75f, 0.426f);
        g10 = new GroceryItem("Coca-Cola 12-pack", 3.49f, 5.112f);
        g11 = new GroceryItem("Toilet Paper - 48 pack", 40.96f, 10.89f);
        g12 = new RefrigeratorItem("MILK", 2.99f, 2.06f);
        g13 = new RefrigeratorItem("Eggs", 1.79f, 0.77f);
        g14 = new RefrigeratorItem("Yogurt", 4.79f, 1.02f);
        g15 = new FreezerItem("Massive Icecream", 67.93f, 15.03f);

        // Make a new customer and add some items to his/her shopping cart
        Shopper c = new Shopper();
        c.addItem(g1);
        c.addItem(g2);
        c.addItem(g3);
        c.addItem(g4);
        c.addItem(g5);
        c.addItem(g6);
        c.addItem(g7);
        c.addItem(g8);
        c.addItem(g9);
        c.addItem(g10);
        c.addItem(g1);

        c.addItem(g6);
        c.addItem(g2);
        c.addItem(g2);
        c.addItem(g3);
        c.addItem(g3);
        c.addItem(g3);
        c.addItem(g3);
        c.addItem(g3);
        c.addItem(g10);
        c.addItem(g11);
        c.addItem(g9);
        c.addItem(g5);
        c.addItem(g6);
        c.addItem(g7);
        c.addItem(g8);
        c.addItem(g8);
        c.addItem(g8);
        c.addItem(g5);
        c.addItem(g12);
        c.addItem(g13);
        c.addItem(g14);
        c.addItem(g15);

        System.out.println("\n INITIAL CART CONTENTS:");
        for (int i = 0; i < c.getNumItems(); i++) {
            System.out.println(" " + c.getCart()[i]);
        }

        System.out.println("\n CART CONTENTS: ");
        c.packBags();
        System.out.println(c.displayCartContents());
    }

}

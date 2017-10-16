import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by tariq on 12/03/17.
 */
public class BuildingDialog extends Dialog {
    public BuildingDialog(Stage owner, String title, Building Buildinginput) {
    setTitle(title);
        // Set the button types
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType);
        GridPane gridPane = new GridPane();

        int NumberofRooms = 0;
        int  totalSize = 0;

        int index = 0;
        int Stringindex =0;

        //String array that holds all the people living in the building

        //forloop to find total siz

        for(int floorplanIterator =0; floorplanIterator<Buildinginput.getFloorPlans().length-1;floorplanIterator++) {
            FloorPlan currentFloorPlan = Buildinginput.getFloorPlans()[floorplanIterator];
            NumberofRooms += currentFloorPlan.getNumberOfRooms();
        }

        String[] DirectoryArray = new String[NumberofRooms];
        String[] floorplanArray = new String[NumberofRooms];


        //Iterate though entire floorarray
        for(int flooriterator = 0; flooriterator<Buildinginput.getFloorPlans().length;flooriterator++){
            FloorPlan currentFloorPlan = Buildinginput.getFloorPlans()[flooriterator];
            //iterate through the rooms of that floorplan
            for (int i = 0; i < FloorBuilderView.ROOM_COLORS.length; i++) {
                //stores Alll the tiles in the
                if(currentFloorPlan.roomWithColor(i)instanceof Room) {
                    Room temp = currentFloorPlan.roomWithColor(i);
                    //totalSize += temp.getNumberOfTiles();

                    DirectoryArray[index++] = temp.getNumber() + " - " + temp.getOccupant() + " ("+temp.getPosition() + ")";
                    floorplanArray[Stringindex++] = Buildinginput.getFloorPlan(flooriterator).getName();
                }
                //currentFloorPlan.roomWithColor(i).getPosition()
            }
        }

int booleanCounter= 0;
        for( int i = 0; i< Buildinginput.getFloorPlans().length;i++){
            for(int x = 0;x<20;x++){
                for(int y = 0;y<20;y++){
                    if(Buildinginput.getFloorPlan(i).getWallarray()[x][y] == true){
                        booleanCounter++;
                    }
                }
            }

        }
totalSize = (Buildinginput.getFloorPlan(0).size()* Buildinginput.getFloorPlan(0).size()*Buildinginput.getNumFloors()) - booleanCounter ;
        System.out.println(Buildinginput.getFloorPlans()[0].size());


        // Create the username and password labels and fields.
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Label NumFLoors, NumExits, NumROoms, TotalSize;
        TextField NumfloorsField, NumExitsField, NumRoomsField, TotalSizeField;
        Button DirectoryListingBUtton;


        NumFLoors = new Label("Num Floors:");
        NumExits = new Label("Num Exits:");
        NumROoms = new Label("Num Rooms:");
        TotalSize = new Label("Total Size:");

        NumfloorsField = new TextField();
        NumfloorsField.setText("" + Buildinginput.getNumFloors());
        NumfloorsField.setEditable(false);

        NumExitsField = new TextField();
        NumExitsField.setText(""+ Buildinginput.getNumExits());
        NumExitsField.setEditable(false);

        NumRoomsField = new TextField();
        NumRoomsField.setText("" + NumberofRooms);
        NumRoomsField.setEditable(false);

        TotalSizeField = new TextField();
       TotalSizeField.setText("" + (totalSize) + " Sq. Ft.");
        TotalSizeField.setEditable(false);

        DirectoryListingBUtton = new Button("Directory Listing");
        DirectoryListingBUtton.setMinWidth(150);



        gridPane.add(NumFLoors, 0, 0);
        gridPane.add(NumfloorsField, 1, 0);
        gridPane.add(NumExits, 0, 1);
        gridPane.add(NumExitsField, 1, 1);
        gridPane.add(NumROoms, 0, 2);
        gridPane.add(NumRoomsField, 1, 2);
        gridPane.add(TotalSize, 0, 3);
        gridPane.add(TotalSizeField, 1, 3);
        gridPane.add(DirectoryListingBUtton, 0, 4, 2, 1);

        DirectoryListingBUtton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Question 4
                Dialog directoryListingDialog = new DirectoryDialog(DirectoryArray,floorplanArray);
                directoryListingDialog.showAndWait();
            }
        });
        //.setScene(new Scene(aPane, 285,205));   // Set size of window
        getDialogPane().setPrefSize(50,100);
        getDialogPane().setContent(gridPane);

    }
}

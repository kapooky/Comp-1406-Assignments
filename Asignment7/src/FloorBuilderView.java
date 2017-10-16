/**
 * Created by tariq on 05/03/17.
 */

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class FloorBuilderView extends GridPane {
    public static final String[] ROOM_COLORS =
            {"ORANGE", "YELLOW", "LIGHTGREEN", "DARKGREEN",
                    "LIGHTBLUE", "BLUE", "CYAN", "DARKCYAN",
                    "PINK", "DARKRED", "PURPLE", "GRAY"};
    public int SelectedRadioOption;
    public int index;
    private Building model;            // The model to which this view is attached
    // The user interface components needed by the controller
    private Button[][] btnarray;
    private RadioButton WallsRB, ExitsRB, RoomTilesRB, SelectRoomrb;
    private Button buildingOverviewbtn, colorpickerbutton;
    private Label layoutlabel, selectlabel, Floormainsummarylabel;
    private TextField BottomTextField;


    public FloorBuilderView(Building m) {
        model = m;            // Store the model for access later
        GridPane gridbtnpane = new GridPane();
        GridPane rightSideGridPane = new GridPane();
        VBox radiobuttons = new VBox();


        //create and position the labels
        layoutlabel = new Label("FLOOR LAYOUT");

        selectlabel = new Label(("SELECT/EDIT:"));
        selectlabel.setMinHeight(20);
        selectlabel.setMinHeight(20);

        Floormainsummarylabel = new Label("FLOOR SUMMARY:");
        buildingOverviewbtn = new Button("Building Overview");
        setValignment(buildingOverviewbtn, VPos.TOP);
        BottomTextField = new TextField("Main floor with 0 rooms."); //SET THE TEXT


        //gridbuttonarray
        gridbtnpane = new GridPane();


        //This is all the stuff done pertaining to RADIOBUTTONS
        ToggleGroup togglegroup = new ToggleGroup();
        WallsRB = new RadioButton("Walls");
        ExitsRB = new RadioButton("Exits");
        RoomTilesRB = new RadioButton("Room Tiles");
        SelectRoomrb = new RadioButton("Select Room");
        //set toggle group, easier way to do this stuff?
        WallsRB.setToggleGroup(togglegroup);
        ExitsRB.setToggleGroup(togglegroup);
        RoomTilesRB.setToggleGroup(togglegroup);
        SelectRoomrb.setToggleGroup(togglegroup);

        radiobuttons.getChildren().addAll(WallsRB, ExitsRB, RoomTilesRB, SelectRoomrb);
        radiobuttons.setSpacing(10);


        colorpickerbutton = new Button();
        colorpickerbutton.setMinHeight(25);
        colorpickerbutton.setMinWidth(25);
        colorpickerbutton.setDisable(true);

        add(layoutlabel, 0, 0);
        add(selectlabel, 1, 0);
        add(rightSideGridPane, 1, 1, 1, 1);


        add(gridbtnpane, 0, 1, 1, 2);

        add(Floormainsummarylabel, 0, 3);
        add(BottomTextField, 0, 4, 3, 1);


        rightSideGridPane.add(radiobuttons, 0, 0, 1, 1);
        rightSideGridPane.add(buildingOverviewbtn, 0, 1, 1, 2);
        rightSideGridPane.add(colorpickerbutton, 1, 0);

        setMargin(rightSideGridPane, new Insets(0, 10, 10, 10));
        setMargin(buildingOverviewbtn, new Insets(10, 0, 0, 0));
        //colorbutton
        btnarray = new Button[20][20];
        for (int w = 0; w < 20; w++) {
            for (int h = 0; h < 20; h++) {
                btnarray[w][h] = new Button();
                btnarray[w][h].setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
                btnarray[w][h].setMinSize(5, 5);

                gridbtnpane.add(btnarray[w][h], w, h);


            }
        }

        ColumnConstraints column0 = new ColumnConstraints(300, 300, 1200);
        ColumnConstraints column1 = new ColumnConstraints(200);
        column0.setHgrow(Priority.ALWAYS);
        column1.setHgrow(Priority.ALWAYS);

        getColumnConstraints().addAll(column0, column1);

        setValignment(colorpickerbutton, VPos.CENTER);
        setHalignment(colorpickerbutton, HPos.LEFT);


        // Add all the components to the window
        getChildren().addAll();

        //SetPadding for components
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(10);
        //   Floormainsummarylabel.setPadding(new Insets(10,0,10,0));

        // Call update() to make sure model contents are shown
        update();
    }

    // public methods to allow access to JComponents
    public Button getbtnfromArray(int x, int y) {
        return btnarray[x][y];
    }

    public RadioButton getWallsRB() {
        return WallsRB;
    }

    public RadioButton getExitsRB() {
        return ExitsRB;
    }

    public RadioButton getRoomTilesRB() {
        return RoomTilesRB;
    }

    public Button getColorpickerbutton() {
        return colorpickerbutton;
    }

    // This method is called whenever the model changes, to make
    // sure that the view shows the model's current state
    public void update() {
        for (int x = 0; x < model.getFloorPlan(0).size(); x++) {
            for (int y = 0; y < model.getFloorPlan(0).size(); y++) {

                Room temp = model.getFloorPlan(0).roomAt(x, y);
                if (model.hasExitAt(x, y)) {
                    btnarray[x][y].setStyle("-fx-base: RED;");
                    btnarray[x][y].setText("EXIT");
                } else if (model.getFloorPlan(0).wallAt(x, y)) {
                    btnarray[x][y].setStyle("-fx-base: BLACK;");
                } else if (temp instanceof Room) {
                    btnarray[x][y].setStyle(String.format("%s%s%s", "-fx-base:", ROOM_COLORS[temp.getColorIndex()], ";"));
                } else {
                    btnarray[x][y].setStyle("-fx-base: WHITE");
                }
            }
        }

        colorpickerbutton.setStyle(String.format("%s%s", "-fx-base:", ROOM_COLORS[index]));
        if (RoomTilesRB.isSelected()) {

        }


    }


}





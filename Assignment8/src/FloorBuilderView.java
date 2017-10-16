import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class FloorBuilderView extends GridPane {
    public static final String[]    ROOM_COLORS = {"ORANGE", "YELLOW", "LIGHTGREEN", "DARKGREEN",
            "LIGHTBLUE", "BLUE", "CYAN", "DARKCYAN",
            "PINK", "DARKRED", "PURPLE", "GRAY"};

    // These are the tiles and buttons
    private GridPane        tiles;
    private Button[][]      buttons;
    private RadioButton     editWallsButton;
    private RadioButton     selectExitsButton;
    private RadioButton     editRoomsButton;
    private RadioButton     defineRoomsButton;
    private Button          colorButton;
    private Button          overviewButton;
    private TextField       summaryField;

    private  Label summaryLabel,floorLabel,selectLabel;

    //These are the menu compononts
    public int index,currentSelectedROOM;
    public  MenuItem        fourthFLoor, thirdFloor, secondFloor,MainFloor,basementFLoor;

    public Button getFloorTileButton(int r, int c) { return buttons[c][r]; }
    public Pane getTilePanel() { return tiles; }
    public RadioButton getEditWallsButton() { return editWallsButton; }
    public RadioButton getSelectExitsButton() { return selectExitsButton; }
    public RadioButton getEditRoomsButton() { return editRoomsButton; }
    public RadioButton getDefineRoomsButton() { return defineRoomsButton; }
    public Button getRoomColorButton() { return colorButton; }
    public Button getBuildingOverviewButton() { return overviewButton; }
    public TextField getSummaryField() { return summaryField; }


    private Building       model;

    public FloorBuilderView(Building m) {
        model = m;


        VBox topPane = new VBox();

        // Setup the pane with the floor plan buttons
        buttons = new Button[model.getFloorPlan(0).size()][model.getFloorPlan(0).size()];
        tiles = new GridPane();
        tiles.setPadding(new Insets(0, 0, 0, 10));
        for (int r=0; r<model.getFloorPlan(0).size(); r++) {
            for (int c=0; c<model.getFloorPlan(0).size(); c++) {
                buttons[r][c] = new Button();
                buttons[r][c].setPrefWidth(200);
                buttons[r][c].setPrefHeight(200);
                buttons[r][c].setMinHeight(10);
                buttons[r][c].setMinWidth(10);
                tiles.add(buttons[r][c], r, c);
            }
        }

        // Add the labels
        floorLabel  = new Label("FLOOR LAYOUT");
        floorLabel.setMinHeight(30);
        floorLabel.setMinWidth(100);
        setMargin(floorLabel, new Insets(0, 0, 0, 10));

        selectLabel = new Label("SELECT/EDIT:");
        selectLabel.setMinHeight(30);
        selectLabel.setMinWidth(100);
        setMargin(selectLabel, new Insets(0, 0, 0, 0));

        summaryLabel = new Label("FLOOR SUMMARY:");
        summaryLabel.setMinHeight(30);
        summaryLabel.setMinWidth(100);
        setMargin(summaryLabel, new Insets(0, 0, 0, 10));

        // Add the Editting buttons
        ToggleGroup operations = new ToggleGroup();
        editWallsButton = new RadioButton("Walls");
        editWallsButton.setToggleGroup(operations);
        editWallsButton.setSelected(true);
        editWallsButton.setMinHeight(30);
        editWallsButton.setMinWidth(70);
        setMargin(editWallsButton, new Insets(0, 0, 0, 20));

        selectExitsButton = new RadioButton("Exits");
        selectExitsButton.setToggleGroup(operations);
        add(selectExitsButton,1,2,2,1);
        selectExitsButton.setMinHeight(30);
        selectExitsButton.setMinWidth(70);
        setMargin(selectExitsButton, new Insets(0, 0, 0, 20));

        editRoomsButton = new RadioButton("Room Tiles");
        editRoomsButton.setToggleGroup(operations);
        editRoomsButton.setMinHeight(30);
        editRoomsButton.setMinWidth(80);
        setMargin(editRoomsButton, new Insets(0, 20, 0, 20));

        defineRoomsButton = new RadioButton("Select Room");
        defineRoomsButton.setToggleGroup(operations);
        defineRoomsButton.setMinHeight(30);
        defineRoomsButton.setMinWidth(80);
        setMargin(defineRoomsButton, new Insets(0, 20, 0, 20));

        // Add the room color label
        colorButton = new Button();
        colorButton.setMinHeight(30);
        colorButton.setMinWidth(30);
        colorButton.setPrefWidth(30);
        colorButton.setStyle("-fx-base: WHITE;");
        setMargin(colorButton, new Insets(0, 10, 0, 0));

        // Add the Building Overview button
        overviewButton = new Button("Building Overview");
        overviewButton.setMinHeight(30);
        overviewButton.setMinWidth(140);
        overviewButton.setPrefWidth(140);
        setMargin(overviewButton, new Insets(20, 0, 0, 10));
        setValignment(overviewButton, VPos.TOP);
        setHalignment(overviewButton, HPos.LEFT);


        /*MENU COMPONENTS*/
        Menu fileMenu = new Menu("Select Floor");
        fourthFLoor = new MenuItem(model.getFloorPlan(3).getName());
        thirdFloor = new MenuItem(model.getFloorPlan(2).getName());
        secondFloor = new MenuItem(model.getFloorPlan(1).getName());
        MainFloor = new MenuItem(model.getFloorPlan(0).getName());
        basementFLoor = new MenuItem(model.getFloorPlan(4).getName());
        fileMenu.getItems().addAll(fourthFLoor,thirdFloor,secondFloor,MainFloor,
                new SeparatorMenuItem(),basementFLoor);

        // Add the menus to a menubar and then add the menubar to the pane
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);



        topPane.getChildren().addAll(menuBar);

        // Add the summary Field
        summaryField = new TextField("");
        summaryField.setMinHeight(30);
        summaryField.setMinWidth(300);
        summaryField.setEditable(false);
        setMargin(summaryField, new Insets(0, 10, 10, 10));


        add(menuBar,0,0,3,1);
        add(selectLabel,1,1,1,1);
        add(summaryField,0,9,3,1);
        add(colorButton,2,4,1,1);
        add(tiles, 0, 2, 1, 6);

        add(overviewButton,1,7,2,1);
        add(editWallsButton,1,3,2,1);
        add(defineRoomsButton,1,5,2,1); // selectrp
        add(editRoomsButton,1,4,1,1);

        add(floorLabel,0,1,1,1);
         add(summaryLabel,0,8,1,1);
    }

    public void update(int currentFloor, int currentColor) {
        int dimension = model.getFloorPlan(currentFloor).size();

        // Update the Room Color Button
        colorButton.setDisable(!editRoomsButton.isSelected());
        colorButton.setStyle("-fx-base: " + ROOM_COLORS[currentColor] + ";");

        // Update the walls, exits and room colors
        for (int r=0; r<dimension; r++) {
            for (int c=0; c<dimension; c++) {
                if (model.exitAt(c,r) != null) {
                    buttons[r][c].setText("EXIT");
                    buttons[r][c].setStyle("-fx-base: RED; -fx-text-fill: WHITE;");
                }
                else if (model.getFloorPlan(currentFloor).wallAt(c,r)) {
                    buttons[r][c].setText("");
                    buttons[r][c].setStyle("-fx-base: BLACK;");
                }
                else if (model.getFloorPlan(currentFloor).roomAt(c,r) != null) {
                    Room off = model.getFloorPlan(currentFloor).roomAt(c,r);
                    buttons[r][c].setText("");
                    buttons[r][c].setStyle("-fx-base: " + ROOM_COLORS[off.getColorIndex()]  + ";");
                }
                else {
                    buttons[r][c].setText("");
                    buttons[r][c].setStyle("-fx-base: WHITE;");
                }
            }
        }

        // Update the summary field
        summaryField.setText(model.getFloorPlan(currentFloor).getName() + " with " +  model.getFloorPlan(currentFloor).getNumberOfRooms() + " rooms.");
    }
}
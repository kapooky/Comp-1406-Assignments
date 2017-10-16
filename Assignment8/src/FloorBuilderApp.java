import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FloorBuilderApp extends Application  {
    private FloorBuilderView   view;
    private Building           model;
    private int                currentFloor;    // Index of the floor being displayed
    private int                currentColor;    // Index of the current room color

    //Constants needed for selecting Which room
    public final int MAINFLOOR_SELECTED = 0;
    public final int SECONFLOOR_SELECTED = 1;
    public final int THIRDFLOOR_SELECTED = 2;
    public final int FOURTHFLOOR_SELECTED = 3;
    public final int BASEMENT_SELECTED = 4;

    public void start(Stage primaryStage) {
        model = Building.example();
        currentFloor = 0;
        currentColor = 0;

        VBox aPane = new VBox();
        view = new FloorBuilderView(model);
        view.setPrefWidth(Integer.MAX_VALUE);
        view.setPrefHeight(Integer.MAX_VALUE);

        //ALERT Dialogue
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText("You must select a tile that belongs to a room");

        view.getBuildingOverviewButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Question 4
                Dialog buildingDialog = new BuildingDialog(primaryStage, "Building Overview",model);
                buildingDialog.showAndWait();
                view.update(currentFloor,currentColor);
            }
        });





        //EVENT HANDLERS FOR MENU
        view.basementFLoor.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                currentFloor = BASEMENT_SELECTED;
                view.update(currentFloor,currentColor);
            }
        });
        view.MainFloor.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                currentFloor = MAINFLOOR_SELECTED;
                view.update(currentFloor,currentColor);
            }
        });
        view.secondFloor.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                currentFloor = SECONFLOOR_SELECTED;
                view.update(currentFloor,currentColor);
            }
        });
        view.thirdFloor.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                currentFloor = THIRDFLOOR_SELECTED;
                view.update(currentFloor,currentColor);
            }
        });

        view.fourthFLoor.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                currentFloor = FOURTHFLOOR_SELECTED;
                view.update(currentFloor,currentColor);
            }
        });




        aPane.getChildren().add(view);
        primaryStage.setTitle("Floor Plan Builder");
        primaryStage.setScene(new Scene(aPane, 370,360));
        primaryStage.show();

        //ALERT Dialogue
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Selection");
        alert.setHeaderText(null);
        alert.setContentText("You must select a tile that belongs to a room");

        // Plug in the floor panel event handlers:
        for (int r=0; r<model.getFloorPlan(0).size(); r++) {
            for (int c=0; c<model.getFloorPlan(0).size(); c++) {
                view.getFloorTileButton(r, c).setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        handleTileSelection(event);
                    }});
            }
        }

        // Plug in the radioButton event handlers
        view.getEditWallsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getSelectExitsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getEditRoomsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});
        view.getDefineRoomsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                view.update(currentFloor, currentColor);
            }});

        // Plug in the office color button
        view.getRoomColorButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                currentColor = (currentColor + 1)%view.ROOM_COLORS.length;
                view.update(currentFloor, currentColor);
            }});

        view.update(currentFloor, currentColor);
    }

    public Boolean IsSelectedPieceNull(int x, int y){
        if(model.getFloorPlan(currentFloor).roomAt(x,y) == null){
            return true;
        }
        return false;
    }



    // Handle a Floor Tile Selection
    private void handleTileSelection(ActionEvent e) {
        // Determine which row and column was selected
        int r=0, c=0;
        OUTER:
        for (r=0; r<model.getFloorPlan(0).size(); r++) {
            for (c=0; c<model.getFloorPlan(0).size(); c++) {
                if (e.getSource() == view.getFloorTileButton(r, c))
                    break OUTER;
            }
        }

        // Check if we are in edit wall mode, then toggle the wall
        if (view.getEditWallsButton().isSelected()) {
            model.getFloorPlan(currentFloor).setWallAt(r, c, !model.getFloorPlan(currentFloor).wallAt(r, c));
            // Remove this tile from the room if it is on one, because it is now a wall
            Room room = model.getFloorPlan(currentFloor).roomAt(r, c);
            if (room != null)
                room.removeTile(r, c);
        }

        // Otherwise check if we are in edit exits mode
        else if (view.getSelectExitsButton().isSelected()) {
            if (model.exitAt(r, c) != null)
                model.removeExit(r, c);
            else {
                model.addExit(r, c);
                // Remove this tile from the room if it is on one, because it is now an exit
                Room off = model.getFloorPlan(currentFloor).roomAt(r, c);
                if (off != null)
                    off.removeTile(r, c);
            }
        }

        // Otherwise check if we are selecting a room tile
        else if (view.getEditRoomsButton().isSelected()) {
            if (!model.getFloorPlan(currentFloor).wallAt(r, c) && !model.hasExitAt(r, c)) {
                Room room = model.getFloorPlan(currentFloor).roomAt(r, c);
                if (room != null) {
                    room.removeTile(r, c);
                    if (room.getNumberOfTiles() == 0)
                        model.getFloorPlan(currentFloor).removeRoom(room);
                }
                else {
                    room = model.getFloorPlan(currentFloor).roomWithColor(currentColor);
                    if (room == null) {
                        room = model.getFloorPlan(currentFloor).addRoomAt(r, c);
                        room.setColorIndex(currentColor);
                    }
                    else {
                        room.addTile(r, c);
                    }
                }
            }
        }

        else if(view.getDefineRoomsButton().isSelected()){
            if(IsSelectedPieceNull(r,c)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Selection");
                alert.setHeaderText(null);
                alert.setContentText("You must select a tile that belongs to a room");
                alert.showAndWait();
            }
            else{
                // Now bring up the dialog box
                Dialog dialog = new RoomInfoDialog("Room Details",model.getFloorPlan(currentFloor).roomAt(r,c),model.getFloorPlan(currentFloor));
                dialog.showAndWait();

            }
        }
        // Otherwise do nothing
        else {
        }




        view.update(currentFloor, currentColor);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
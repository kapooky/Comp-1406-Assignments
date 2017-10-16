import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FloorBuilderApp extends Application {
    public final int WALLS_SELECTED = 1;
    public final int EXIT_SELECTED = 2;
    public final int ROOM_SELECTED = 3;
    private Building model;
    private FloorBuilderView view;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        model = new Building(4, 5);
        model = model.example();
        view = new FloorBuilderView(model);

        //Event Handlers
        view.getColorpickerbutton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                view.index++;
                if (view.index > view.ROOM_COLORS.length - 1) {
                    view.index = 0;
                }


                view.update();
            }
        });
        //Radiobuttons event Handlers
        view.getWallsRB().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                view.SelectedRadioOption = WALLS_SELECTED;
                view.getColorpickerbutton().setDisable(true);


            }
        });
        view.getExitsRB().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                view.SelectedRadioOption = EXIT_SELECTED;
                view.getColorpickerbutton().setDisable(true);


            }
        });

        view.getRoomTilesRB().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                view.SelectedRadioOption = ROOM_SELECTED;
                view.getColorpickerbutton().setDisable(false);


            }
        });
        for (int w = 0; w < 20; w++) {
            for (int h = 0; h < 20; h++) {
                view.getbtnfromArray(w, h).setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        Button temp = (Button) mouseEvent.getSource();
                        int Xposition = 0;
                        int Yposition = 0;
                        for (int x = 0; x < model.getFloorPlan(0).size(); x++) {
                            for (int y = 0; y < model.getFloorPlan(0).size(); y++) {
                                if (view.getbtnfromArray(x, y) == temp) {
                                    Xposition = x;
                                    Yposition = y;
                                }
                            }
                        }

                        if (view.SelectedRadioOption == 1) {
                            handleWallbuttonSelected(Xposition, Yposition);

                        } else if (view.SelectedRadioOption == 2) {

                            handleExitbuttonSelected(Xposition, Yposition, temp);

                        } else if (view.SelectedRadioOption == 3) {
                            handleRoombuttonSelected(Xposition, Yposition);

                        } else {
                            view.getColorpickerbutton().setDisable(true);
                        }

                    }

                });

            }
        }


        primaryStage.setTitle("Slide Puzzle Application");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(view, 500, 350));
        primaryStage.show();

        // Update the view upon startup
        view.update();
    }

    public void handleWallbuttonSelected(int x, int y) {
        //if there there is no wall at this position, OR  no exit at this position then set a wall.
        if ((!model.getFloorPlan(0).wallAt(x, y) && (!model.hasExitAt(x, y)))) {


            if (model.getFloorPlan(0).roomAt(x, y) != null) {
                model.getFloorPlan(0).roomAt(x, y).removeTile(x, y);
            }
            model.getFloorPlan(0).setWallAt(x, y, true);
        } else {
            //other wise, set the wall to false,... REMOVE THE WALL
            model.getFloorPlan(0).setWallAt(x, y, false);
        }

        view.update();
    }

    public void handleExitbuttonSelected(int x, int y, Button temp) {
        //System.out.println("HelloWORLDDDDDDDDDADAD");
        if (!model.hasExitAt(x, y)) {
            // System.out.println("hello");

            if (model.getFloorPlan(0).roomAt(x, y) != null) {
                model.getFloorPlan(0).roomAt(x, y).removeTile(x, y);
            }

            model.addExit(x, y);
        } else {
            model.removeExit(x, y);
            temp.setText("");

        }
        view.update();
    }

    public void handleRoombuttonSelected(int x, int y) {
        // System.out.println("helloWo000000rld");
        FloorPlan tempFloor = model.getFloorPlan(0);

        //if there is a room with the selected color, then add a tile
        if ((!model.getFloorPlan(0).wallAt(x, y) && (!model.hasExitAt(x, y)))) {
            if (tempFloor.roomWithColor(view.index) instanceof Room) {
                int index = tempFloor.roomWithColor(view.index).getColorIndex();

                if (tempFloor.roomWithColor(view.index) == tempFloor.roomAt(x, y)) {
                    tempFloor.roomWithColor(view.index).removeTile(x, y);


                } else if (tempFloor.roomAt(x, y) == null) {
                    tempFloor.roomWithColor(view.index).addTile(x, y);


                } else {
                    tempFloor.roomAt(x, y).removeTile(x, y);
                    //tempFloor.roomWithColor(view.index).removeTile(x,y);
                    tempFloor.roomWithColor(view.index).addTile(x, y);

                }


            } else if (tempFloor.roomAt(x, y) instanceof Room) {
                tempFloor.roomAt(x, y).removeTile(x, y);
                tempFloor.addRoomAt(x, y).setColorIndex(view.index);
            }
            // if not, then make a new new room with the selected color
            else {
                if (tempFloor.roomAt(x, y) == null) {
                    tempFloor.addRoomAt(x, y).setColorIndex(view.index);
                } else {


                }


            }
        }
        view.update();

    }
}
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SliderPuzzleApp extends Application {
    private SliderPuzzleGame model;
    private SliderPuzzleView view;

    private GamePiece selectedPiece;
    private boolean justGrabbed;
    private int lastX;
    private int lastY;


    public void start(Stage primaryStage) {
        model = new SliderPuzzleGame();
        view = new SliderPuzzleView(model);


        // Add event handlers to the inner game board buttons
        for (int w = 1; w <= (GameBoard.WIDTH); w++) {
            for (int h = 1; h <= (GameBoard.HEIGHT); h++) {

                view.getGridSection(w, h).setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        handleGridSectionSelection(mouseEvent);

                    }
                });
                view.getGridSection(w, h).setOnMouseReleased(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        if (selectedPiece instanceof GamePiece) {
                            model.makeAMove();
                            view.update();
                        }
                        if (selectedPiece instanceof GoalPiece) {
                            if (selectedPiece.getTopLeftX() >= 5 && selectedPiece.getTopLeftY() >= 2) {
                                model.completeBoard();
                                for (int w = 1; w < 7; w++) {
                                    for (int h = 1; h < 7; h++) {
                                        view.getGridSection(w, h).setStyle("-fx-base: WHITE;");
                                    }
                                }
                            }
                        }


                    }
                });
                view.getGridSection(w, h).setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mouseEvent) {
                        handleGridSectionMove(mouseEvent);
                    }
                });
            }
        }

        // Plug in the Start button and NeaxtBoard button event handlers
        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.startBoard();
                view.update();
            }
        });
        view.getNextBoardButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.moveToNextBoard();
                view.update();
            }
        });

        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.startBoard();
                view.update();
            }
        });

        primaryStage.setTitle("Slide Puzzle Application");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(view, -10 + SliderPuzzleView.GRID_UNIT_SIZE * (GameBoard.WIDTH + 2), 45 + SliderPuzzleView.GRID_UNIT_SIZE * (GameBoard.HEIGHT + 2)));
        primaryStage.show();

        // Update the view upon startup
        view.update();
    }


    private void handleGridSectionSelection(MouseEvent mouseEvent) {

        float getPointX = (float) mouseEvent.getSceneX() / 40;
        float getPointY = (float) mouseEvent.getSceneY() / 40;

        int valuex = 0;
        int valuey = 0;

        lastX = (int) mouseEvent.getX();
        lastY = (int) mouseEvent.getY();


        for (int i = 1; i < 7; i++) {
            int min = i;
            int max = i + 1;
            if (getPointX >= min && getPointX <= max) {
                valuex = min;

            }

        }
        for (int i = 1; i < 7; i++) {
            int min = i;
            int max = i + 1;

            if (getPointY >= min && getPointY <= max) {
                valuey = min;
            }

        }


        selectedPiece = model.getCurrentBoard().pieceAt(valuex, valuey);

    }

    private void handleGridSectionMove(MouseEvent mouseEvent) {
        int currentGridX = (int) mouseEvent.getX();
        int currentGridY = (int) mouseEvent.getY();
        int AbsdifferenceX = Math.abs(currentGridX - lastX);
        int AbsdifferenceY = Math.abs(currentGridY - lastY);
        if (selectedPiece instanceof GamePiece) {
            if (AbsdifferenceX >= view.GRID_UNIT_SIZE || AbsdifferenceY >= view.GRID_UNIT_SIZE) {
                if (AbsdifferenceX > AbsdifferenceY) {
                    if ((currentGridX - lastX) > 0) {
                        if (selectedPiece.canMoveRightIn(model.getCurrentBoard())) {
                            selectedPiece.moveRight();
                            view.update();
                        }


                    } else {
                        if (selectedPiece.canMoveLeftIn(model.getCurrentBoard())) {
                            selectedPiece.moveLeft();
                            view.update();
                        }

                    }
                } else {
                    if ((currentGridY - lastY) < 0) {
                        if (selectedPiece.canMoveUpIn(model.getCurrentBoard())) {
                            selectedPiece.moveUp();
                            view.update();
                        }

                    } else {
                        if (selectedPiece.canMoveDownIn(model.getCurrentBoard())) {
                            selectedPiece.moveDown();
                            view.update();
                        }

                    }

                }
            }


        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GridPaneExample extends Application {
    public void start(Stage primaryStage) {
        GridPane aPane = new GridPane();
        aPane.setPadding(new Insets(10, 10, 10, 10));
        aPane.setHgap(0);
        aPane.setVgap(1);

        for (int row = 1; row <= 6; row++)
            for (int col = 1; col <= 8; col++) {
                Button b = new Button();
                // Make the buttons bigger than we want.  They will be
                // re-sized to fit within the shrunken pane.
                b.setPrefWidth(200);
                b.setPrefHeight(200);

                if (Math.random() < 0.5)
                    b.setStyle("-fx-base: WHITE;");
                else
                    b.setStyle("-fx-base: BLACK;");
                aPane.add(b, col, row);
            }

        primaryStage.setTitle("Simple GridPane Example");
        primaryStage.setScene(new Scene(aPane, 420, 320));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
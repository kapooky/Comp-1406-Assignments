import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

/**
 * Created by tariqazmat on 3/13/2017.
 */
public class DirectoryDialog extends Dialog {
    public DirectoryDialog(String[] directoryArray, String[] floorplanArray) {
        //We want to iterate through all rooms and find the ppl living there


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        ListView<String> directoryListingview;
        Button searchButton;

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType);

        searchButton = new Button("Search");
        searchButton.setMinWidth(400);

        directoryListingview = new ListView<String>();
        directoryListingview.setItems(FXCollections.observableArrayList(directoryArray));
        directoryListingview.setMinSize(350, 100);

        //ADD grid components
        grid.add(directoryListingview, 0, 0);
        grid.add(searchButton, 0, 1);

        //Event handler for the search Button
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                String storeResult;

                //create the the Input required Dialog
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Input Required");
                dialog.setHeaderText(null);
                dialog.setContentText("Please enter the full name of the person you are searching for: ");

                //Create the the search dialog
                Alert alertMatch = new Alert(Alert.AlertType.INFORMATION);
                alertMatch.setTitle("Search results");
                alertMatch.setHeaderText(null);

                String[] tokens;
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    if(directoryArray.length == 0){
                        alertMatch.setContentText("That name does not match anyone in our records, please try again");
                    }
                    storeResult = result.get();
                    for (int i = 0; i < directoryArray.length; i++) {
                        if (directoryArray[i] instanceof String) {
                            tokens = directoryArray[i].split("[^a-zA-Z0-9 ]");
                            if (tokens[1].trim().equals(storeResult.trim())) {
                                alertMatch.setContentText(tokens[1] + "is our " + tokens[2] + " and can be located on the " + floorplanArray[i] + " in room " + tokens[0]);
                                break;
                            }
                        }
                        alertMatch.setContentText("That name does not match anyone in our records, please try again");
                    }
                    alertMatch.showAndWait();
                }
            }
        });

        setTitle("Directory Listing");
        getDialogPane().setPrefSize(350, 400);
        getDialogPane().setContent(grid);

    }
}

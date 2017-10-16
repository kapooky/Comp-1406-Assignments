import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;


import java.net.URI;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class RoomInfoDialog extends Dialog {
    public RoomInfoDialog(String title, Room roominput, FloorPlan floorplan) {
        setTitle(title);


        // Set the button types
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        TextField OccupantField = new TextField();
        OccupantField.setPromptText("Person who occupied this room");
        if (roominput.getOccupant() != null)
        OccupantField.setText(roominput.getOccupant());
        OccupantField.setMinWidth(500);

        TextField PositionField = new TextField();
        PositionField.setPromptText("Job Position/Title of this position");
        if (roominput.getPosition() != null)
        PositionField.setText(roominput.getPosition());

        PositionField.setMinWidth(500);

        TextField RoomNumberFied = new TextField();
        RoomNumberFied.setPromptText("The Room Number");
        if (roominput.getNumber() != null)
        RoomNumberFied.setText(roominput.getNumber());

        RoomNumberFied.setMinWidth(250);

        TextField FloorPositionField = new TextField();
        // FloorPlan temp = (FloorPlan)(roominput.);
        FloorPositionField.setText(floorplan.getName());
        FloorPositionField.setEditable(false);
        FloorPositionField.setMinWidth(500);

        TextField SizePositionFIeld = new TextField();
        SizePositionFIeld.setText("" + roominput.getNumberOfTiles() + " Sq. Ft.");
        SizePositionFIeld.setEditable(false);
        SizePositionFIeld.setMinWidth(500);

        Button RoomNumberButton = new Button();
        RoomNumberButton.setStyle(String.format("%s%s", "-fx-base:", FloorBuilderView.ROOM_COLORS[roominput.getColorIndex()]));
        RoomNumberButton.setMinWidth(250);
        RoomNumberButton.setFocusTraversable(false);

        grid.add(new Label("Occupant:"), 0, 0);
        grid.add(OccupantField, 1, 0,2,1);
        grid.add(new Label("Position:"), 0, 1);
        grid.add(PositionField, 1, 1,2,1);
        grid.add(new Label("Number:"), 0, 2);
        //the funny row
        grid.add(RoomNumberFied, 1, 2);
        grid.add(RoomNumberButton,2,2);


        //   grid.add(PositionField, 1, 1);
        grid.add(new Label("Floor:"), 0, 3);
        grid.add(FloorPositionField, 1, 3,2,1);
        grid.add(new Label("Size:"), 0, 4);
        grid.add(SizePositionFIeld,1,4,2,1);
        getDialogPane().setContent(grid);

        // Enable/Disable OK button depending on whether username was entered.
        Node okButton = getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true); // Disable upon start



        OccupantField.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {
                okButton.setDisable(OccupantField.getText().trim().isEmpty() ||
                        PositionField.getText().trim().isEmpty() ||
                        RoomNumberFied.getText().trim().isEmpty());
            }
        });

        // Enable/Disable OK button depending on whether address was entered.
        PositionField.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                okButton.setDisable(OccupantField.getText().trim().isEmpty() ||
                        PositionField.getText().trim().isEmpty() ||
                        RoomNumberFied.getText().trim().isEmpty());
            }
        });
        RoomNumberFied.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {
                okButton.setDisable(RoomNumberFied.getText().trim().isEmpty() ||
                        PositionField.getText().trim().isEmpty() ||
                        OccupantField.getText().trim().isEmpty());
            }
        });






        setResultConverter(new Callback<ButtonType, Room>() {
            public Room call(ButtonType b) {
                if (b == okButtonType) {
                        roominput.setOccupant(OccupantField.getText());
                        roominput.setPosition((PositionField.getText()));
                        roominput.setNumber(RoomNumberFied.getText());
                        return roominput;


                    // Desktop.getDesktop().open(new File("the.mp4"));
                }
                return null;
            }
        });
    }
}

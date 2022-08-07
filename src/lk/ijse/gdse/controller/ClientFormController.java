package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ClientFormController{
    public AnchorPane stageBar;
    public TextArea textArea;
    public JFXTextField textField;
    public JFXButton btnSend;
    public FontAwesomeIconView btnImages;
    public JFXToggleButton tglDarkMode;
    public Label lblUser;
    public AnchorPane mainPane;

    public void initialize() {
    }

    public void sendOnAction(ActionEvent actionEvent) {
    }

    public void btnSendEnteredMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color: #8ded6f;  -fx-background-radius: 7;");
    }

    public void btnSendExitedMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color:  #6fbbed; -fx-background-radius: 7;");
    }

    public void closeOnAction(MouseEvent event) {
    }

    public void minimizeOnAction(MouseEvent event) {
    }

    public void sendImagesOnAction(MouseEvent event) {
    }

    public void darkModeOnAction(ActionEvent actionEvent) {

    }
}

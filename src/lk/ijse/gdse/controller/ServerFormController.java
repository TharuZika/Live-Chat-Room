package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ServerFormController {
    public AnchorPane stageBar;
    public TextArea textArea;
    public JFXTextField textField;
    public JFXButton btnSend;

    public void sendOnAction(ActionEvent actionEvent) {
    }

    public void btnSendEnteredMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 7; ");
    }

    public void btnSendExitedMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color:  LightBlue; ");
    }
}

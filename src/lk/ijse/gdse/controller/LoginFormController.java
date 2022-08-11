package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtIp;
    public JFXTextField txtUserName;
    public static String userName;
    public static String hostIp;
    public AnchorPane rootPane;
    public Label lblError;
    public AnchorPane borderErrorUsername;
    public AnchorPane borderErrorIp;

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {

        if (txtUserName.getText().equals("")){

            borderErrorIp.setVisible(true);
            borderErrorUsername.setVisible(true);
            lblError.setVisible(true);
            txtIp.setStyle("-fx-faint-focus-color: red;" + "-fx-focus-color:red;" + "-jfx-unfocus-color:red");
            txtUserName.setStyle("-fx-faint-focus-color: red;" + "-fx-focus-color:red;" + "-jfx-unfocus-color:red");

        }else {

            userName = txtUserName.getText();
            hostIp = txtIp.getText();

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/gdse/view/ClientForm.fxml"))));
            stage.setTitle(userName);

            System.out.println("IP Address: " + hostIp + "\nUsername: " + userName);
        }

    }
}

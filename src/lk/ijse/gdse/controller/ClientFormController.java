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
import lk.ijse.gdse.bp.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientFormController{
    public AnchorPane stageBar;
    public static TextArea textArea;
    public static JFXTextField textField;
    public JFXButton btnSend;
    public FontAwesomeIconView btnImages;
    public JFXToggleButton tglDarkMode;
    public Label lblUser;
    public Label lblAddress;
    public AnchorPane mainPane;

    final int PORT = 5000;
    public FontAwesomeIconView btnEmojis;
    private Socket socket;
    private String hostIp;
    private String userName;
    private Client client;

    public void initialize(){
        this.hostIp = LoginFormController.hostIp;
        this.userName = LoginFormController.userName;
        lblUser.setText(userName);
        lblAddress.setText(hostIp);

        try {
            socket = new Socket(hostIp, PORT);
            client = new Client(socket, userName);
            client.receiveMessage();
            client.sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void messageReceived(String message){
        textArea.appendText(message);
    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        String msgToSend = textField.getText();
        if (!msgToSend.isEmpty()){

            messageReceived(msgToSend);
            client.sendMessage(msgToSend);
            textField.clear();
        }
    }

    public void btnSendEnteredMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color:  #3fc469;  -fx-background-radius: 7;");
    }

    public void btnSendExitedMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color:   #1495ff; -fx-background-radius: 7;");
    }


    public void sendImagesOnAction(MouseEvent event) {
    }

    public void openEmojiPanelOnAction(MouseEvent event) {
        try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_WINDOWS);
            r.keyPress(KeyEvent.VK_PERIOD);
            r.keyRelease(KeyEvent.VK_WINDOWS);
            r.keyRelease(KeyEvent.VK_PERIOD);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
}

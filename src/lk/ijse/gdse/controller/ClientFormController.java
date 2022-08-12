package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.bp.Client;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class ClientFormController{

    public FontAwesomeIconView icoGreen;
    public Label lblConnected;
    @FXML
    public TextArea textArea;
    @FXML
    JFXTextField textField;
    public JFXButton btnSend;
    public FontAwesomeIconView btnImages;
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
            Socket socket = new Socket(hostIp,PORT);
            client=new Client(socket,userName);
            client.receiveMessage(textArea);
            client.send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.receiveMessage(textArea);

    }

    public static void addLabel(String message, TextArea textArea){
        textArea.appendText(message+"\n");
    }

    public void sendOnAction(ActionEvent actionEvent)  {
        String msgToSend=textField.getText();

        addLabel("Me: "+msgToSend, textArea);

        client.send(msgToSend);
        textField.clear();
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

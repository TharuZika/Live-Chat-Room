package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
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
    public static TextArea textArea;
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
        System.out.println("1");
//        String msgToSend = textField.getText();
        System.out.println("2");
//        if (!textField.getText().isEmpty()){
        textField.setText("ane hutto");

//            messageReceived(textField.getText());
//            System.out.println("4");
//            client.sendMessage(textField.getText());
//            System.out.println("5");
//            textField.clear();
//            System.out.println("6");
//        }
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

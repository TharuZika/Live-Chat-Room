package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.scene.control.Button;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.bp.Client;
import javafx.geometry.Insets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class ClientFormController{

    public Label lblConnected;
    public ScrollPane scrollPane;
    public VBox vBox;
    @FXML
    JFXTextField textField;
    public JFXButton btnSend;
    public FontAwesomeIconView btnImages;
    public Label lblUser;
    public AnchorPane mainPane;

    final int PORT = 5000;
    public FontAwesomeIconView btnEmojis;
    private Socket socket;
    private String hostIp;
    private String userName;
    private Client client;

    public void initialize(){


        messageSendToEve("You Joined the Chat", vBox);
        System.out.println("You are connected to the Server");

        this.hostIp = LoginFormController.hostIp;
        this.userName = LoginFormController.userName;

        System.out.println("Username: "+userName);
        System.out.println("Host IP: "+hostIp+":"+PORT);

        lblUser.setText(userName);
        try {
            socket = new Socket(hostIp,PORT);
            client=new Client(socket,userName);
            client.receiveMessage(vBox);
            client.send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.receiveMessage(vBox);
    }

    public static void messageSendToEve(String message, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,10,5,10));

        Text text=new Text(message);
        text.setStyle("-fx-font: 15 arial;");
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(151,233,255); " +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });

    }

    public void sendOnAction(ActionEvent actionEvent)  {
        if (!textField.getText().isEmpty()) {

            String message = textField.getText();

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text text=new Text(message);
            text.setFill(Color.color(0.934,0.945,0.996));
            text.setStyle("-fx-font: 15 arial;");
            TextFlow textFlow=new TextFlow(text);
            textFlow.setStyle("-fx-color: rgb(239,242,255);" + "-fx-background-color: rgb(8,142,73);" + "-fx-background-radius: 18px");
            textFlow.setPadding(new Insets(5,10,5,10));

            hBox.getChildren().add(textFlow);
            vBox.getChildren().add(hBox);


            client.send(message);
            textField.clear();
            textField.requestFocus();

            if (textField.getText().equals("exit")){
                Stage window = (Stage) btnSend.getScene().getWindow();
                window.close();
            }

        }else {
            textField.requestFocus();
        }
    }

    public void btnSendEnteredMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color:  #088e49;  -fx-background-radius: 7;");
    }

    public void btnSendExitedMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color:   #a7a7a7; -fx-background-radius: 7;");
    }


    public void sendImagesOnAction(MouseEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (!selectedFile.getPath().isEmpty()) {
            client.send(selectedFile.getPath());
            textField.requestFocus();
            messageSendToEve(selectedFile.getPath(), vBox);
        }else {
            textField.requestFocus();
        }
    }

    public void openEmojiPanelOnAction(MouseEvent event) {

//        textField.requestFocus();
//        try {
//            Robot r = new Robot();
//            r.keyPress(KeyEvent.VK_WINDOWS);
//            r.keyPress(KeyEvent.VK_PERIOD);
//            r.keyRelease(KeyEvent.VK_WINDOWS);
//            r.keyRelease(KeyEvent.VK_PERIOD);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
        Stage stage = new Stage();

        byte[] smiling_eyes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x81};
        String emoji1 = new String(smiling_eyes, Charset.forName("UTF-8"));

        byte[] tears_of_joy = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x82};
        String emoji2 = new String(tears_of_joy, Charset.forName("UTF-8"));

        byte[] heart_shaped_eyes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x83};
        String emoji3 = new String(heart_shaped_eyes, Charset.forName("UTF-8"));

        byte[] winking_eye = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x84};
        String emoji4 = new String(winking_eye, Charset.forName("UTF-8"));

        byte[] smiling_open_mouth = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x85};
        String emoji5 = new String(smiling_open_mouth, Charset.forName("UTF-8"));

        Button button1 = new Button(emoji1);
        Button button2 = new Button(emoji2);
        Button button3 = new Button(emoji3);
        Button button4 = new Button(emoji4);
        Button button5 = new Button(emoji5);

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(350, 300);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(button1, 1, 0);
        gridPane.add(button2, 1, 1);
        gridPane.add(button3, 2, 0);
        gridPane.add(button4, 2, 1);
        gridPane.add(button5, 3, 0);

        Scene scene = new Scene(gridPane, 350, 300);
        stage.setTitle("Grid Layout");
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        stage.initStyle(StageStyle.UTILITY);
        stage.show();


    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mainPane.getScene().getWindow();
        window.setTitle("LOGIN");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
    }
}

package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.gdse.bp.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class ClientFormController{

    public Label lblConnected;
    public ScrollPane scrollPane;
    public VBox vBox;
    public AnchorPane rootEmoji;
    public JFXButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19,btn20;
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
    public static String userName;
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

//    public static void imagesSendToEve(VBox vBox) throws IOException {
//        String selectedFile = "E:\\GDSE60\\WorkingPlace\\Working\\Live-Chat\\src\\lk\\ijse\\gdse\\assets\\download\\down.jpg";
//            HBox hBox = new HBox();
//            hBox.setAlignment(Pos.CENTER_LEFT);
//            hBox.setPadding(new Insets(5,5,5,10));
//
//            FileInputStream fileInputStream=new FileInputStream(selectedFile);
//            Image image = new Image(fileInputStream, 200,250,false,false);
//            ImageView view=new ImageView(image);
//            hBox.getChildren().add(view);
//            vBox.getChildren().add(hBox);
//
//
//
//    }

    public static void messageSendToEve(String message, VBox vBox) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 10, 5, 10));

            Text text = new Text(message);
            text.setStyle("-fx-font: 15 arial;");
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: rgb(151,233,255); " +
                    "-fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            hBox.getChildren().add(textFlow);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vBox.getChildren().add(hBox);
                }
            });
    }


    public void sendOnAction(ActionEvent actionEvent)  {
        rootEmoji.setVisible(false);
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


    public void sendImagesOnAction(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (!selectedFile.getPath().isEmpty()) {

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            FileInputStream fileInputStream=new FileInputStream(selectedFile.getPath());
            Image image = new Image(fileInputStream, 200,250,false,false);
            ImageView view=new ImageView(image);
            hBox.getChildren().add(view);
            vBox.getChildren().add(hBox);

            client.send(selectedFile.getPath());

//            client.sendImages(selectedFile.getAbsoluteFile().getCanonicalPath());

        }else {
            textField.requestFocus();
        }
    }

    public void openEmojiPanelOnAction(MouseEvent event) throws UnsupportedEncodingException {
        if (!rootEmoji.isVisible()) {
            rootEmoji.setVisible(true);
        }else {
            rootEmoji.setVisible(false);
        }
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

    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mainPane.getScene().getWindow();
        window.setTitle("LOGIN");
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
    }

    public void emojiSetOnAction(ActionEvent actionEvent) {
        byte[] natural_face = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x90};
        String emoji1 = new String(natural_face, Charset.forName("UTF-8"));

        byte[] relaxed = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x83};
        String emoji2 = new String(relaxed, Charset.forName("UTF-8"));

        byte[] smiley = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x84};
        String emoji3 = new String(smiley, Charset.forName("UTF-8"));

        byte[] sweat_smile = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x85};
        String emoji4 = new String(sweat_smile, Charset.forName("UTF-8"));

        byte[] joy = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x82};
        String emoji5 = new String(joy, Charset.forName("UTF-8"));

        byte[] sunglass = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x8E};
        String emoji6 = new String(sunglass, Charset.forName("UTF-8"));

        byte[] yum = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x88};
        String emoji7 = new String(yum, Charset.forName("UTF-8"));

        byte[] wink = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x89};
        String emoji8 = new String(wink, Charset.forName("UTF-8"));

        byte[] relived = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x87};
        String emoji9 = new String(relived, Charset.forName("UTF-8"));

        byte[] smirk = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x8F};
        String emoji10 = new String(smirk, Charset.forName("UTF-8"));

        byte[] sweart = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x93};
        String emoji11 = new String(sweart, Charset.forName("UTF-8"));

        byte[] cry = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xA2};
        String emoji12 = new String(cry, Charset.forName("UTF-8"));

        byte[] sob = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xAD};
        String emoji13 = new String(sob, Charset.forName("UTF-8"));

        byte[] scream = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xB1};
        String emoji14 = new String(scream, Charset.forName("UTF-8"));

        byte[] angry = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0xA1};
        String emoji15 = new String(angry, Charset.forName("UTF-8"));

        byte[] heart = new byte[]{(byte)0xE2, (byte)0x99, (byte)0xA5};
        String emoji16 = new String(heart, Charset.forName("UTF-8"));

        byte[] thumbsup = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x91, (byte)0x8D};
        String emoji17 = new String(thumbsup, Charset.forName("UTF-8"));

        byte[] v = new byte[]{(byte)0xE2, (byte)0x9C, (byte)0x8C};
        String emoji18 = new String(v, Charset.forName("UTF-8"));

        byte[] muscle = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x92, (byte)0xAA};
        String emoji19 = new String(muscle, Charset.forName("UTF-8"));

        byte[] middle_finger = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x96, (byte)0x95};
        String emoji20 = new String(middle_finger, Charset.forName("UTF-8"));




        JFXButton btn = (JFXButton) actionEvent.getSource();
        switch (btn.getId()){
            case "btn1" :
                textField.requestFocus();
                textField.appendText(emoji1);
                break;
            case "btn2" :
                textField.requestFocus();
                textField.appendText(emoji2);
                break;
            case "btn3" :
                textField.requestFocus();
                textField.appendText(emoji3);
                break;
            case "btn4" :
                textField.requestFocus();
                textField.appendText(emoji4);
                break;
            case "btn5" :
                textField.requestFocus();
                textField.appendText(emoji5);
                break;
            case "btn6" :
                textField.requestFocus();
                textField.appendText(emoji6);
                break;
            case "btn7" :
                textField.requestFocus();
                textField.appendText(emoji7);
                break;
            case "btn8" :
                textField.requestFocus();
                textField.appendText(emoji8);
                break;
            case "btn9" :
                textField.requestFocus();
                textField.appendText(emoji9);
                break;
            case "btn10" :
                textField.requestFocus();
                textField.appendText(emoji10);
                break;
            case "btn11" :
                textField.requestFocus();
                textField.appendText(emoji11);
                break;
            case "btn12" :
                textField.requestFocus();
                textField.appendText(emoji12);
                break;
            case "btn13" :
                textField.requestFocus();
                textField.appendText(emoji13);
                break;
            case "btn14" :
                textField.requestFocus();
                textField.appendText(emoji14);
                break;
            case "btn15" :
                textField.requestFocus();
                textField.appendText(emoji15);
                break;
            case "btn16" :
                textField.requestFocus();
                textField.appendText(emoji16);
                break;
            case "btn17" :
                textField.requestFocus();
                textField.appendText(emoji17);
                break;
            case "btn18" :
                textField.requestFocus();
                textField.appendText(emoji18);
                break;
            case "btn19" :
                textField.requestFocus();
                textField.appendText(emoji19);
                break;
            case "btn20" :
                textField.requestFocus();
                textField.appendText(emoji20);
                break;
        }
    }

    public void closeEmojiPaneOnAction(ActionEvent actionEvent) {
        rootEmoji.setVisible(false);
    }
}

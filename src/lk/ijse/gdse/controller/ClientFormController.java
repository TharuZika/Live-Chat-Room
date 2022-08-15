package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.zip.InflaterOutputStream;

public class ClientFormController{

    private static Object img;
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

        try {
            customizeEmoji();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void imagesSendToEve(BufferedImage image) throws IOException {
        ImageIO.write(image, "jpg", new File("E:\\GDSE60\\WorkingPlace\\Working\\Live-Chat\\src\\lk\\ijse\\gdse\\assets\\download\\test.jpg"));

    }

    public static void messageSendToEve(String message, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,10,5,10));
        if (message.startsWith("C:")){
            try {
                System.out.println(message);
                FileInputStream fileInputStream = new FileInputStream(message);
                Image image = new Image(fileInputStream, 200, 250, false, false);
                ImageView view = new ImageView(image);
                hBox.getChildren().add(view);
                vBox.getChildren().add(hBox);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }else {
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

            client.sendImages(selectedFile.getAbsoluteFile().getCanonicalPath());

        }else {
            textField.requestFocus();
        }
    }

    public void customizeEmoji() throws UnsupportedEncodingException {
        byte[] a = { (byte)0xf0, (byte)0x9f, (byte)0x98, (byte)0x88 };
        String s = new String(a,"UTF-8");
        byte[] b = s.getBytes("UTF-16BE");
        for ( byte c : b ) { System.out.printf("%02x ",c); }


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

        {btn1.setText(emoji1);
        btn2.setText(emoji2);
        btn3.setText(emoji3);
        btn4.setText(emoji4);
        btn5.setText(emoji5);
        btn6.setText(emoji1);
        btn7.setText(emoji2);
        btn8.setText(emoji3);
        btn9.setText(emoji4);
        btn10.setText(emoji5);
        btn11.setText(emoji1);
        btn12.setText(emoji2);
        btn13.setText(emoji3);
        btn14.setText(emoji4);
        btn15.setText(emoji5);
        btn16.setText(emoji1);
        btn17.setText(emoji2);
        btn18.setText(emoji3);
        btn19.setText(emoji4);
        btn20.setText(emoji5);}



//        GridPane gridPane = new GridPane();
//        gridPane.setMinSize(350, 300);
//        gridPane.setPadding(new Insets(2, 15, 2, 0));
//        gridPane.setVgap(5);
//        gridPane.setHgap(5);
//        gridPane.setAlignment(Pos.CENTER);
//
//        gridPane.add(btn1, 1, 0);
//        gridPane.add(btn2, 1, 1);
//        gridPane.add(btn3, 2, 0);
//        gridPane.add(btn4, 2, 1);
//        gridPane.add(btn5, 3, 0);

//        rootEmoji.getChildren().add(gridPane);


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
        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JFXButton btn = (JFXButton) event.getSource();
                if (btn.getId().equals(btn1.getId()))
                    System.out.println("emoji1");
                else if (btn.getId().equals(btn2.getId()))
                    System.out.println("emoji2");
            }
        };
    }

    public void closeEmojiPaneOnAction(ActionEvent actionEvent) {
        rootEmoji.setVisible(false);
    }
}

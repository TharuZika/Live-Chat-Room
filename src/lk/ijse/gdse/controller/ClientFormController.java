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

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientFormController{
    public AnchorPane stageBar;
    public TextArea textArea;
    public JFXTextField textField;
    public JFXButton btnSend;
    public FontAwesomeIconView btnImages;
    public JFXToggleButton tglDarkMode;
    public Label lblUser;
    public AnchorPane mainPane;

    final int PORT = 5000;
    Socket accept;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    BufferedReader in;
    PrintWriter out;

    public void run() throws IOException {
        accept = new Socket("localhost", PORT);

        in = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        out = new PrintWriter(accept.getOutputStream(), true);

        while (true){
            String line = in.readLine();

            if (line.startsWith("SUBMITNAME")) {
                out.println("name");
            }else if (line.startsWith("NAMEACCEPTED")){
                textField.setEditable(true);
            }else if (line.startsWith("MESSAGE")){
                textArea.appendText(line.substring(8)+"\n");
            }
        }
    }

    public void initialize(){
    }


//    public void initialize() {
//        new Thread(() -> {
//            try {
//                socket = new Socket("localhost",PORT);
//
//                dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                dataInputStream = new DataInputStream(socket.getInputStream());
//
//                while (!massage.equals("exit")) {
//                    massage = dataInputStream.readUTF();
//                    textArea.appendText("\n"+massage);
//                }
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        out.println(textField.getText());
        textField.clear();
//        dataOutputStream.writeUTF(name+" : "+textField.getText().trim());
//        dataOutputStream.flush();
//        textArea.appendText("\n"+name+" : "+textField.getText().trim());
//        textField.clear();
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

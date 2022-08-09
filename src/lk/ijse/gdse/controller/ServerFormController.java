package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    public AnchorPane stageBar;
    public TextArea textArea;
    public JFXTextField textField;
    public JFXButton btnSend;
    public AnchorPane mainPane;

    final int PORT = 5000;
    ServerSocket serverSocket;
    Socket accept;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;

    String name = "Server";
    String massage = "";

    public void initialize(){
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Server Started..");
                accept = serverSocket.accept();
                System.out.println("Client Connected..");

                dataOutputStream = new DataOutputStream(accept.getOutputStream());
                dataInputStream = new DataInputStream(accept.getInputStream());

                bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                while (!massage.equals("exit")) {
                    massage = dataInputStream.readUTF();
                    textArea.appendText("\n"+massage);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(name+" : "+textField.getText().trim());
        dataOutputStream.flush();
        textArea.appendText("\n"+name+" : "+textField.getText().trim());
        textField.clear();
    }

    public void btnSendEnteredMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color: #8ded6f;  -fx-background-radius: 7;");
    }

    public void btnSendExitedMouse(MouseEvent event) {
        btnSend.setStyle("-fx-background-color:  #6fbbed; -fx-background-radius: 7;");
    }
}

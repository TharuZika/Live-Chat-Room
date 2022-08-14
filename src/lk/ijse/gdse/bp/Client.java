package lk.ijse.gdse.bp;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import lk.ijse.gdse.controller.ClientFormController;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Client implements Initializable{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public Client(Socket socket, String userName){
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = userName;
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void send(String msg){
        try {
//            while (socket.isConnected()){
            bufferedWriter.write(userName+": "+msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
//            }
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void send(){
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void sendImages(BufferedImage bufferedImage, Image image){
        try {
            Graphics graphics = bufferedImage.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
//            bufferedWriter.write(ImageIO.write(bufferedImage, "jpg"));
            bufferedWriter.write(String.valueOf(image));
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

//    public void listenForMsg(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (socket.isConnected()){
//                    try {
//                        msgFromGroupChat=bufferedReader.readLine();
////                        ClientFormController clientFormController=new ClientFormController();
////                        clientFormController.showMsg(msgFromGroupChat);
//                        System.out.println(msgFromGroupChat);///////////////////////
//                    }catch (IOException e){
//                        closeEverything(socket,bufferedReader,bufferedWriter);
//                    }
//                }
//            }
//        }).start();
//    }

    public void receiveMessage(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String msgFromChat=bufferedReader.readLine();
                        ClientFormController.messageSendToEve(msgFromChat, vBox);
                    }catch (IOException e){
                        closeEverything(socket,bufferedReader,bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        this.receiveMessage(vBox);
//        this.send();
    }
}


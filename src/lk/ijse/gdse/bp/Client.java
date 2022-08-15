package lk.ijse.gdse.bp;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import lk.ijse.gdse.controller.ClientFormController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

public class Client implements Initializable{

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;
    OutputStream outputStream;
    InputStream inputStream;
    ImageIcon imageIcon;

    public Client(Socket socket, String userName){
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = socket.getOutputStream();
            this.inputStream = socket.getInputStream();
            this.userName = userName;
        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    public void sendImages(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);

            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            outputStream.write(size);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();

            socket.close();

        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }



    }

    public void send(String msg){
        try {
                bufferedWriter.write(userName + ": " + msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
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


    public void receiveMessage(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String msgFromChat=bufferedReader.readLine();
                        ClientFormController.messageSendToEve(msgFromChat, vBox);

                        byte[] sizeAr = new byte[4];
                        inputStream.read(sizeAr);
                        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                        byte[] imageAr = new byte[size];
                        inputStream.read(imageAr);

                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                        ClientFormController.imagesSendToEve(image);
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


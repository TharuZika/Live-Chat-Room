package lk.ijse.gdse.bp;

import javafx.fxml.Initializable;
import lk.ijse.gdse.controller.ClientFormController;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Client implements Initializable {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;
    private String hostIp;

    public Client(Socket socket, String userName){
        this.socket=socket;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = userName;
        } catch (IOException e) {
            end(socket, bufferedReader, bufferedWriter);
        }
    }


//Inner Class Start Here ----------------------
    public static class Handler implements Runnable{
        public ArrayList<Handler> handlers = new ArrayList<>();

        private Socket socket;
        static String userName;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;

        public Handler(Socket socket){
            this.socket = socket;
            try {
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.userName = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.handlers.add(this);
            System.out.println(userName+" Connected to the Server");

        }

        @Override
        public void run() {
            while (socket.isConnected()){
                try {
                    String clientMessage = bufferedReader.readLine();
                    sendMessageToServer(clientMessage);
                } catch (IOException e) {
                    handlers.remove(this);
                    end(socket,bufferedReader,bufferedWriter);
                }
            }
        }
        
        public void sendMessageToServer(String message){
            for (Handler handler : handlers){
                try {
                    if (!handler.userName.equals(userName)) {
                        handler.bufferedWriter.write(message);
                        handler.bufferedWriter.newLine();
                        handler.bufferedWriter.flush();
                    }
                }catch (IOException e){
                    handlers.remove(this);
                    end(socket,bufferedReader,bufferedWriter);
                }
            }
        }
        
    }
//Inner Class End Here ----------------------



    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void sendMessage(String message){
        try {
            bufferedWriter.write(userName+" :"+message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            end(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(){

        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
           end(socket, bufferedReader, bufferedWriter);
        }
    }

    public static void end(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
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

    public void receiveMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String messageRec = bufferedReader.readLine();
                        ClientFormController.messageReceived(messageRec);
                    } catch (IOException e) {
                        end(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

}

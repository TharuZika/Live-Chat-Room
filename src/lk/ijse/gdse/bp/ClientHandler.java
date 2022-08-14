package lk.ijse.gdse.bp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> handlers=new ArrayList<ClientHandler>();
    public static ArrayList<String> users = new ArrayList<String>();

    private Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    private String userName;

    public ClientHandler(Socket socket){
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));


            this.userName = bufferedReader.readLine();

            users.add(userName);
            handlers.add(this);

            serverMessage(userName+" Joined the Chat");

        }catch (IOException e){
            endClient(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()){
            try {
                String message=bufferedReader.readLine();
                serverMessage(message);
            }catch (IOException e){
                endClient(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }

    public void serverMessage(String message){
        for (ClientHandler clientHandler : handlers){
            try {
                if (!clientHandler.userName.equals(userName)){
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                endClient(socket,bufferedReader,bufferedWriter);
            }
        }
    }


    public void endClient(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        users.remove(this);
        handlers.remove(this);
        serverMessage(userName+" Left the Chat");
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
}

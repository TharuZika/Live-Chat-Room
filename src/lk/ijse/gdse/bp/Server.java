package lk.ijse.gdse.bp;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println(Client.Handler.userName+" User Connected");
                Client.Handler handler = new Client.Handler(socket);

                Thread thread = new Thread(handler);
                thread.start();
            }
        }catch (IOException e){
            try {
                if (serverSocket != null){
                    serverSocket.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

}

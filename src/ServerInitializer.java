import lk.ijse.gdse.bp.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInitializer {
    private static final int PORT = 5000;
    private ServerSocket serverSocket;

    public ServerInitializer(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Server is Online");
        ServerSocket serverSocket = new ServerSocket(PORT);
        ServerInitializer server = new ServerInitializer(serverSocket);
        server.startServer();
    }

    public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A client trying to connect");
                ClientHandler handler = new ClientHandler(socket);

                Thread thread = new Thread(handler);
                thread.start();
            }
        }catch (IOException e){
            close();
        }

    }
    public void close(){
        System.out.println("Server is Offline");
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

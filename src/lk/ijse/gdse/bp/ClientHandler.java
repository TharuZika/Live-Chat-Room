package lk.ijse.gdse.bp;

import lk.ijse.gdse.controller.ClientFormController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> handlers=new ArrayList<ClientHandler>();
    public static ArrayList<String> users = new ArrayList<String>();

    private Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
//    InputStream inputStream;
//    OutputStream outputStream;
    private String userName;

    public ClientHandler(Socket socket){
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.inputStream=socket.getInputStream();
//            this.outputStream = socket.getOutputStream();


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
//            Image read -------------------->
//            try {
//                byte[] sizeAr = new byte[4];
//                inputStream.read(sizeAr);
//                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//
//                byte[] imageAr = new byte[size];
//                inputStream.read(imageAr);
//
//                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//                System.out.println("wait to process Image");
//                ImageIO.write(image, "jpg", new File("E:\\GDSE60\\WorkingPlace\\Working\\Live-Chat\\src\\lk\\ijse\\gdse\\assets\\download\\down.jpg"));
//                System.out.println("done process Image");
//
////                processImage(image);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

//    private void processImage(BufferedImage image) throws IOException {
////      BufferedImage image = ImageIO.read(new File("E:\\GDSE60\\WorkingPlace\\Working\\Live-Chat\\src\\lk\\ijse\\gdse\\assets\\download\\down.jpg"));
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, "jpg", byteArrayOutputStream);
//        for (ClientHandler clientHandler : handlers) {
//            try {
//                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//                clientHandler.outputStream.write(size);
//                clientHandler.outputStream.write(byteArrayOutputStream.toByteArray());
//                clientHandler.outputStream.flush();
//
//                Thread.sleep(12000);
//                socket.close();
//            }catch (IOException | InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//    }


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

package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;

public class ServerFormController {
    public AnchorPane stageBar;
    public TextArea textArea;
    public JFXTextField textField;
    public JFXButton btnSend;
    public AnchorPane mainPane;

    static final int PORT = 5000;
    Socket accept;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    PrintWriter printWriter;


    private static ArrayList<String> names = new ArrayList<String>();
    private static ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();

    public void initialize() throws IOException {
        System.out.println("Server Started..");
        ServerSocket listner = new ServerSocket(PORT);
        System.out.println("h1");

        try {
            System.out.println("h2");
            while (true){
                System.out.println("h3");
                accept = listner.accept();
                System.out.println("h4");
                Thread handlerThread = new Thread(new Handler(accept));
                System.out.println("h5");
                handlerThread.start();
                System.out.println("h6");
            }
        }finally {
            System.out.println("h5");
            listner.close();
        }
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println("Server Started..");
//        ServerSocket listner = new ServerSocket(PORT);
//
//        try {
//            while (true){
//                Socket socket = listner.accept();
//                Thread handlerThread = new Thread(new Handler(socket));
//                handlerThread.start();
//            }
//        }finally {
//            listner.close();
//        }
//
//
//    }

    private static class Handler implements Runnable{
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket){
            this.socket=socket;
        }

        public void run(){
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {


                    out.println("SUBMITNAME");
                    name = in.readLine();

                    if (name == null){
                        return;
                    }

                    if (!names.contains(names)){
                        names.add(name);
                        break;
                    }
                    out.println("NAMEACCEPTED");
                    writers.add(out);

                    while (true){

                        String input = in.readLine();
                        if (input == null){
                            return;
                        }
                        for (PrintWriter writer : writers){
                            writer.println("MESSAGE "+name+": "+input);
                        }

                    }
                }
            }catch (IOException e){
                e.printStackTrace();

            }finally {
                if (names != null){
                    names.remove(name);
                }
                if (out != null){
                    writers.remove(out);
                }
                try {
                    socket.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

//    public void initialize(){
//        new Thread(() -> {
//            try {
//                serverSocket = new ServerSocket(PORT);
//                System.out.println("Server Started..");
//                try {
//                    while (true) {
//                        accept = serverSocket.accept();
//                        Thread handlerThread = new Thread(new Handler(accept));
//                        handlerThread.start();
//                        System.out.println("Client Connected..");
//                    }
//                }finally {
//                    serverSocket.close();
//                }
//
//                bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
//                printWriter = new PrintWriter(accept.getOutputStream(), true);
//
//
//                while (true) {
////                    massage = dataInputStream.readUTF();
////                    textArea.appendText("\n"+massage);
//
//                    printWriter.println("SUBMITNAME");
//                    name = bufferedReader.readLine();
//
//                    if (name == null){
//                        return;
//                    }
//
//                    if (!names.contains(names)){
//                        names.add(name);
//                        break;
//                    }
//                }
//                printWriter.println("NAMEACCEPTED");
//                writers.add(printWriter);
//
//                while (true){
//
//                    String intput = bufferedReader.readLine();
//                    if (intput == null){
//                        return;
//                    }
//                    for (PrintWriter writer : writers){
//                        writer.println("MESSAGE "+name+": "+intput);
//                    }
//                }
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                if (names !=null){
//                    names.remove(name);
//                }
//                if (printWriter != null){
//                    writers.remove(printWriter);
//                }
//                try {
//                    accept.close();
//                }catch (IOException e){
//
//                }
//            }
//        }).start();
//    }


    public void sendOnAction(ActionEvent actionEvent) throws IOException {
//        bufferedReader.writeUTF(name+" : "+textField.getText().trim());
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
}

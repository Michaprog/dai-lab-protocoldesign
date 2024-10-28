package ch.heig.dai.lab.protocoldesign;

import java.net.*;
import java.io.*;
import static java.nio.charset.StandardCharsets.*;

public class Server {
    final int SERVER_PORT = 1234;


    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    public class Session{

    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {

                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

                    String line;
                    String[] lineArgs;
                    while ((line = in.readLine()) != null) {
                        // There are two errors here!
                        lineArgs = line.split(" ");
                        switch (lineArgs[0]) {
                            case "ADD":
                                out.write(ADD(lineArgs[1], lineArgs[2]));
                                break;
                            case "SUB":
                                out.write(SUB(lineArgs[1], lineArgs[2]));
                                break;
                            case "MUL":
                                out.write(MUL(lineArgs[1], lineArgs[2]));
                                break;
                            case "DIV":
                                out.write(DIV(lineArgs[1], lineArgs[2]));
                                break;
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }

    private static String ADD(String nb1, String nb2){
        int x,y, result;
        x = Integer.parseInt(nb1);
        y = Integer.parseInt(nb2);
        result = x + y;
        return Integer.toString(result);
    }

    private static String SUB(String nb1, String nb2){
        int x,y, result;
        x = Integer.parseInt(nb1);
        y = Integer.parseInt(nb2);
        result = x - y;
        return Integer.toString(result);
    }

    private static String MUL(String nb1, String nb2){
        int x,y, result;
        x = Integer.parseInt(nb1);
        y = Integer.parseInt(nb2);
        result = x * y;
        return Integer.toString(result);
    }

    private static String DIV(String nb1, String nb2){
        int x,y, result;
        x = Integer.parseInt(nb1);
        y = Integer.parseInt(nb2);
        result = x / y;
        return Integer.toString(result);
    }
}
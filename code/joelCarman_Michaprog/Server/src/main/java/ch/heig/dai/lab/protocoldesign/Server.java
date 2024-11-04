package main.java.ch.heig.dai.lab.protocoldesign;

import java.net.*;
import java.io.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    final int SERVER_PORT = 1234;
    public String[] helloMessages;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public Server() {
        helloMessages = new String[5];
        helloMessages[0] = "- ADD <First_number> <Second_number>";
        helloMessages[1] = "- SUB <First_number> <Second_number>";
        helloMessages[2] = "- MULT <First_number> <Second_number>";
        helloMessages[3] = "- DIV <First_number> <Second_number>";
        helloMessages[4] = "- QUIT";
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is listening on port " + SERVER_PORT);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), UTF_8))) {

                    System.out.println("New client connected");

                    // Send the hello message to the client
                    for(String message : helloMessages) {
                        out.write(message);
                        out.newLine();
                        out.flush();
                    }

                    String line;
                    String[] lineArgs;

                    // Handle client commands
                    while ((line = in.readLine()) != null) {
                        lineArgs = line.split(" ");
                        boolean argRight = argsCheck(lineArgs[1], lineArgs[2]);
                        if(argRight) {
                            switch (lineArgs[0]) {
                                case "ADD":
                                    out.write(ADD(lineArgs[1], lineArgs[2]));
                                    out.newLine();
                                    out.flush();
                                    break;
                                case "SUB":
                                    out.write(SUB(lineArgs[1], lineArgs[2]));
                                    out.newLine();
                                    out.flush();
                                    break;
                                case "MULT":
                                    out.write(MUL(lineArgs[1], lineArgs[2]));
                                    out.newLine();
                                    out.flush();
                                    break;
                                case "DIV":
                                    out.write(DIV(lineArgs[1], lineArgs[2]));
                                    out.newLine();
                                    out.flush();
                                    break;
                                case "QUIT":
                                    out.write("Goodbye!");
                                    out.newLine();
                                    out.flush();
                                    return; // End the current client session
                                default:
                                    out.write("Unknown command.");
                                    out.newLine();
                                    out.flush();
                                    break;
                            }
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket exception: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket exception: " + e.getMessage());
        }
    }

    private static String ADD(String nb1, String nb2) {
        int x = Integer.parseInt(nb1);
        int y = Integer.parseInt(nb2);
        int result = x + y;
        return Integer.toString(result);
    }

    private static String SUB(String nb1, String nb2) {
        int x = Integer.parseInt(nb1);
        int y = Integer.parseInt(nb2);
        int result = x - y;
        return Integer.toString(result);
    }

    private static String MUL(String nb1, String nb2) {
        int x = Integer.parseInt(nb1);
        int y = Integer.parseInt(nb2);
        int result = x * y;
        return Integer.toString(result);
    }

    private static String DIV(String nb1, String nb2) {
        int x = Integer.parseInt(nb1);
        int y = Integer.parseInt(nb2);
        if (y == 0) {
            return "Error: Division by zero is not allowed.";
        }
        int result = x / y;
        return Integer.toString(result);
    }

    private static boolean argsCheck(String arg1, String arg2) {
        if(arg1 == null || arg2 == null) {
            throw new IllegalArgumentException();
        }
        for(char c : arg1.toCharArray()) {
            if(!Character.isDigit(c)) {
                throw new IllegalArgumentException();
            }
        }
        for(char c : arg2.toCharArray()) {
            if(!Character.isDigit(c)) {
                throw new IllegalArgumentException();
            }
        }
        return true;
    }
}
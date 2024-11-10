package main.java.ch.heig.dai.lab.protocoldesign;

import java.net.*;
import java.io.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    final int SERVER_PORT = 1234;
    public String helloMessages = "- ADD <First_number> <Second_number>\n" +
            "- SUB <First_number> <Second_number>\n" +
            "- MULT <First_number> <Second_number>\n" +
            "- DIV <First_number> <Second_number>\n" +
            "- QUIT\n";

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
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
                    out.write(helloMessages);
                    // Send the end marker
                    out.write("END");
                    out.newLine();
                    out.flush();

                    String line;
                    String[] lineArgs;

                    // Handle client commands
                    while ((line = in.readLine()) != null) {

                        lineArgs = line.split(" ");
                        if (lineArgs[0].equals("QUIT")) {
                            System.out.println("Session is closed");
                            out.write("Goodbye!");
                            out.newLine();
                            out.flush();
                            break; // End the current client session
                        } else if (lineArgs.length != 3) {
                            out.write("The command length is not correct.");
                            out.newLine();
                            out.flush();
                            continue;
                        }

                        boolean argRight = argsCheck(lineArgs[1], lineArgs[2]);

                        if (argRight) {
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
                                default:
                                    out.write("Unknown command.");
                                    out.newLine();
                                    out.flush();
                                    break;
                            }
                        } else {
                            out.write("Invalide arguments.");
                            out.newLine();
                            out.flush();
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
        return "RESPONSE the operation " + x + " + " + y + " = " + Integer.toString(result);
    }

    private static String SUB(String nb1, String nb2) {
        int x = Integer.parseInt(nb1);
        int y = Integer.parseInt(nb2);
        int result = x - y;
        return "RESPONSE the operation " + x + " - " + y + " = " + Integer.toString(result);
    }

    private static String MUL(String nb1, String nb2) {
        int x = Integer.parseInt(nb1);
        int y = Integer.parseInt(nb2);
        int result = x * y;
        return "RESPONSE the operation " + x + " * " + y + " = " + Integer.toString(result);
    }

    private static String DIV(String nb1, String nb2) {
        int x = Integer.parseInt(nb1);
        int y = Integer.parseInt(nb2);
        if (y == 0) {
            return "Error: Division by zero is not allowed.";
        }
        int result = x / y;
        return "RESPONSE the operation " + x + " / " + y + " = " + Integer.toString(result);
    }

    private static boolean argsCheck(String arg1, String arg2) {
        boolean result = true;
        if (arg1 == null || arg2 == null) {
            result = false;
        }
        for (char c : arg1.toCharArray()) {
            if (!Character.isDigit(c)) {
                result = false;
            }
        }
        for (char c : arg2.toCharArray()) {
            if (!Character.isDigit(c)) {
                result = false;
            }
        }
        return result;
    }
}
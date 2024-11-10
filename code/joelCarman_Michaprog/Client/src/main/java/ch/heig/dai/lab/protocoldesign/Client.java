package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            Scanner scanner = new Scanner(System.in);

            // Read the welcome message from the server
            String line;
            while ((line = in.readLine()) != null && !line.equals("END")) {
                System.out.println("SERVER: " + line);
            }

            String text;
            System.out.print("CLIENT: Please enter a command : ");

            while (true) {
                text = scanner.nextLine();

                // Send a message to the server
                if (text.isEmpty()) {
                    // No command given
                    System.out.print("CLIENT: Please enter a command : ");
                    continue;
                } else if (text.equals("QUIT")) {
                    out.write("QUIT\n");
                    out.flush();
                    System.out.println("CLIENT: Disconnecting....");
                    break;
                }

                out.write(text + "\n");
                out.flush();

                // Read the response from the server
                line = in.readLine();
                if (line == null) {  // Server has closed the connection
                    System.out.println("CLIENT: Connection to server lost.");
                    break;
                }
                System.out.println("SERVER: " + line);

                System.out.print("CLIENT: Please enter a command : ");
            }

        } catch (IOException e) {
            System.out.println("CLIENT: Connection error: " + e.getMessage());
        } finally {
            System.out.println("CLIENT: Client terminated.");
        }
    }
}
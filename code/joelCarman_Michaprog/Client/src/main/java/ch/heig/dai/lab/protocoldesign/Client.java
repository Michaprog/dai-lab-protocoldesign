package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4";
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

            BufferedReader userInput = new BufferedReader(new InputStreamReader(null));

            // read the welcome message from the server
            String welcomeMessage = in.readLine();
            System.out.println(welcomeMessage);

            while (userInput.readLine() != "QUIT") {
                
                // Send a message to the server
                userInput = new BufferedReader(new InputStreamReader(System.in));
                if (userInput.readLine().equals("")) {
                    System.out.println("Please enter a message");
                    continue;
                }else if (userInput.readLine().equals("QUIT")) {
                    out.write("QUIT\n");
                    socket.close();
                    break;
                }
                String message = userInput.readLine();
                out.write(message + "\n");
                out.flush();

                // read the response from the server
                String response = in.readLine();
                System.out.println(response);
            }
            
        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}

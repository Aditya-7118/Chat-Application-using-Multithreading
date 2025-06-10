import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Thread to read messages from server
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(">> " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread reads user input and sends to server
            System.out.println("Enter messages (type 'exit' to quit):");
            String input;
            while (!(input = userInput.readLine()).equalsIgnoreCase("exit")) {
                out.println(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

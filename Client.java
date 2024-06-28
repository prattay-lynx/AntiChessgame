import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9099;
    // can be run using java Client or telnet localhost 9099

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            String serverMessage;
            while ((serverMessage = reader.readLine()) != null) {
                System.out.println(serverMessage);
                if (serverMessage.contains("Your move:")) {
                    String move = consoleReader.readLine();
                    writer.println(move);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

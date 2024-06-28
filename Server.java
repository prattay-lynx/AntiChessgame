import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 9099;
    // can be run using java Server

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started, waiting for players...");
            Socket player1 = serverSocket.accept();
            System.out.println("Player 1 connected.");
            Socket player2 = serverSocket.accept();
            System.out.println("Player 2 connected.");

            ChessGameMain gameHandler = new ChessGameMain(player1, player2);
            gameHandler.startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.Socket;

public class GameHandler {
    private Socket player1;
    private Socket player2;
    private Board board;

    public GameHandler(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
    }

    public void startGame() {
        try (
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                PrintWriter writer1 = new PrintWriter(player1.getOutputStream(), true);
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
                PrintWriter writer2 = new PrintWriter(player2.getOutputStream(), true)) {

            Instructions i = new Instructions();
            String inst = i.executeInstructions();

            printLiChessBanner(writer1);
            writer1.println("Welcome Player 1. You are white.\n");
            writer1.println(inst + "\n");

            printLiChessBanner(writer2);
            writer2.println("Welcome Player 2. You are black.\n");
            writer2.println(inst + "\n");

            boolean player1Turn = true;
            while (true) {
                printBoard(writer1);
                printBoard(writer2);

                boolean moveMade = false;
                if (player1Turn) {
                    writer1.println("Your move: "); // player 1 is white by default
                    while (!moveMade) {
                        String move = reader1.readLine();
                        if (move.equalsIgnoreCase("quit")) {
                            writer2.println("Player 1 has left. You win!");
                            return;
                        }
                        if (board.isCaptureMoveAvailable('W') && !moveContainsCapture(move, 'W')) {
                            writer1.println("You must destroy a piece. Try again.");
                        } else if (board.makeMove(move, 'W')) {
                            moveMade = true;
                            player1Turn = false;
                        } else {
                            writer1.println("Wrong move, try again.");
                        }
                    }
                } else {
                    writer2.println("Your move: "); // player 2 is white by default
                    while (!moveMade) {
                        String move = reader2.readLine();
                        if (move.equalsIgnoreCase("quit")) {
                            writer1.println("Player 2 has left. You win!");
                            return;
                        }
                        if (board.isCaptureMoveAvailable('B') && !moveContainsCapture(move, 'B')) {
                            writer2.println("You must destroy a piece. Try again.");
                        } else if (board.makeMove(move, 'B')) {
                            moveMade = true;
                            player1Turn = true;
                        } else {
                            writer2.println("Wrong move, try again.");
                        }
                    }
                }

                if (board.checkWin()) {
                    if (player1Turn) {
                        writer2.println("You win!");
                        writer1.println("You lose!");
                    } else {
                        writer1.println("You win!");
                        writer2.println("You lose!");
                    }
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                player1.close();
                player2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean moveContainsCapture(String move, char playerColor) {
        String[] parts = move.split(" ");
        if (parts.length != 2) {
            return false;
        }

        int fromRow = 8 - Character.getNumericValue(parts[0].charAt(1));
        int fromCol = parts[0].charAt(0) - 'A';
        int toRow = 8 - Character.getNumericValue(parts[1].charAt(1));
        int toCol = parts[1].charAt(0) - 'A';

        char piece = board.getPiece(fromRow, fromCol);
        char target = board.getPiece(toRow, toCol);

        return target != ' ' && ((Character.isUpperCase(piece) && Character.isLowerCase(target))
                || (Character.isLowerCase(piece) && Character.isUpperCase(target)));
    }

    private void printBoard(PrintWriter writer) {
        writer.println(board.toString());
    }

    private static void printLiChessBanner(PrintWriter w) { // tried my best to make this banner
        String banner = "   _____ _            _       _     \n" +
                "  / ____| |          (_)     | |    \n" +
                " | |    | |__   __ _  _  ___ | |__  \n" +
                " | |    | '_ \\ / _` || |/ __|| '_ \\ \n" +
                " | |____| | | | (_| || |\\__ \\| | | |\n" +
                "  \\_____|_| |_|\\__,_||_||___/|_| |_|\n";

        w.println(banner);
    }
}

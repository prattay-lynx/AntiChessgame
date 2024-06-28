import java.io.*;
import java.net.Socket;

public class ChessGameMain {
    private Socket p1;
    private Socket p2;
    private Board b;

    public ChessGameMain(Socket p1, Socket p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.b = new Board();
    }

    public void startGame() {
        try (
                BufferedReader r1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                PrintWriter w1 = new PrintWriter(p1.getOutputStream(), true);
                BufferedReader r2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                PrintWriter w2 = new PrintWriter(p2.getOutputStream(), true)) {
            Instructions i = new Instructions();

            String RESET = "\u001B[0m";
            // String CYAN = "\u001B[36m";
            String GREEN = "\u001B[32m";
            String RED = "\u001B[31m";
            String YELLOW = "\u001B[33m"; // To print colorful text in CLI

            printLiChessBanner(w1); // print banner
            // w1.println(GREEN + "\nPlease enter your name:" + RESET);
            // String playerName1 = r1.readLine().trim();

            w1.println(GREEN + "\nWelcome Player 1" + "!!!!! You are white.\n" + RESET);
            String inst = i.executeInstructions();
            w1.println(inst + "\n");

            printLiChessBanner(w2); // print banner
            // w2.println(GREEN + "\nPlease enter your name:" + RESET);
            // String playerName2 = r2.readLine().trim();

            w2.println(YELLOW + "\nWelcome Player 2" + "!!!!! You are black.\n" + RESET);
            w2.println(inst + "\n");

            boolean p1Turn = true; // Player 1 gets the first turn
            while (true) {
                printBoard(w1); // Board will be printed everytime the user plays a move
                printBoard(w2);

                if (p1Turn) {
                    w1.println("Your move: ");
                    String move = r1.readLine();
                    if (move.equalsIgnoreCase("quit")) {
                        w2.println("Player 1 has quit. You win!");
                        break;
                    }
                    if (b.makeMove(move, 'W')) { // The first player to connect to the server gets the white piece
                        p1Turn = false;
                    } else {
                        w1.println(RED + "Invalid move, try again." + RESET);
                    }
                } else {
                    w2.println("Your move: ");
                    String move = r2.readLine();
                    if (move.equalsIgnoreCase("quit")) {
                        w1.println("Player 2 has quit. You win!"); // The second player gets the black piece
                        break;
                    }
                    if (b.makeMove(move, 'B')) {
                        p1Turn = true;
                    } else {
                        w2.println(RED + "Invalid move, try again." + RESET);
                    }
                }

                // win condition is checked after each move
                if (b.checkWin()) {
                    if (p1Turn) {
                        w2.println("You win!");
                        w1.println("You lose!");
                    } else {
                        w1.println("You win!");
                        w2.println("You lose!");
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                p1.close();
                p2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printBoard(PrintWriter w) {
        w.println(b.toString());
    }

    private static void printLiChessBanner(PrintWriter w) {
        String banner = "   _____ _            _       _     \n" +
                "  / ____| |          (_)     | |    \n" +
                " | |    | |__   __ _  _  ___ | |__  \n" +
                " | |    | '_ \\ / _` || |/ __|| '_ \\ \n" +
                " | |____| | | | (_| || |\\__ \\| | | |\n" +
                "  \\_____|_| |_|\\__,_||_||___/|_| |_|\n";

        w.println(banner);
    }

}

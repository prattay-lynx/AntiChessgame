public class Instructions {
    public String executeInstructions() {

        String RESET = "\u001B[0m";
        String CYAN = "\u001B[36m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        String YELLOW = "\u001B[33m"; // To print colorful text in CLI

        return RED + "Basic rules:\n\n" + RESET +
                YELLOW + "1. If you can capture an opponent's piece, you must do so.\n" +
                "2. If you have no pieces except your king, you win.\n" +
                "3. If your king is checkmated, you win.\n\n" + RESET +
                RED + "Details:\n\n" + RESET +
                YELLOW + "If you can capture multiple pieces, you may decide which one to take.\n" +
                "- You only have to capture if this is allowed within the normal chess rules - it is not a legal move to put or to leave your king in check.\n"
                +
                "- If your king is in check and you can get out of check by capturing an opponent's piece, you must do so.\n"
                + RESET + GREEN +
                "- If you don't have any legal moves, the game is drawn.\n\n" +
                "- When playing rated games, consider the following:\n\n" +
                "- If your king is checkmated, the server rates it as your loss, which is incompatible to basic rule number 3.\n"
                +
                "- Therefore we refer to \"win\" and \"loss\" exactly the other way round: The server's \"You lose.\" means that you win, and vice versa.\n"
                +
                "- Likewise we use the Elo rating the other way round: A lower rating means a better rating, and vice versa.\n"
                + RESET +

                CYAN + "Have fun!" + RESET;
    }
}

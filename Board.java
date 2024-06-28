import java.util.HashMap;
import java.util.Map;

public class Board {
    private char[][] board;
    private Map<Character, Piece> pieceMap;

    public Board() {
        board = new char[8][8];
        pieceMap = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // pawns are placed here
        for (int i = 0; i < 8; i++) {
            board[1][i] = '\u2659'; // ♙ are the White pawns
            board[6][i] = '\u265F'; // ♟ are the Black pawns
        }

        // rooks are placed here
        board[0][0] = board[0][7] = '\u2656'; // ♖
        board[7][0] = board[7][7] = '\u265C'; // ♜

        // knights are placed here
        board[0][1] = board[0][6] = '\u2658'; // ♘
        board[7][1] = board[7][6] = '\u265E'; // ♞

        // bishops are placed here
        board[0][2] = board[0][5] = '\u2657'; // ♗
        board[7][2] = board[7][5] = '\u265D'; //

        // queens are placed here
        board[0][3] = '\u2655'; // ♕
        board[7][3] = '\u265B'; // ♛

        // kings are placed here
        board[0][4] = '\u2654'; // ♔
        board[7][4] = '\u265A'; // ♚

        // All empty places are filled
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = ' ';
            }
        }

        pieceMap.put('\u2659', new Pawn('W')); // ♙
        pieceMap.put('\u2656', new Rook('W')); // ♖
        pieceMap.put('\u2658', new Knight('W')); // ♘
        pieceMap.put('\u2657', new Bishop('W')); // ♗
        pieceMap.put('\u2655', new Queen('W')); // ♕
        pieceMap.put('\u2654', new King('W')); // ♔
        pieceMap.put('\u265F', new Pawn('B')); // ♟
        pieceMap.put('\u265C', new Rook('B')); // ♜
        pieceMap.put('\u265E', new Knight('B')); // ♞
        pieceMap.put('\u265D', new Bishop('B')); //
        pieceMap.put('\u265B', new Queen('B')); // ♛
        pieceMap.put('\u265A', new King('B')); // ♚
    }

    public boolean makeMove(String move, char playerColor) {
        String[] parts = move.split(" ");
        if (parts.length != 2) {
            return false;
        }

        int fromRow = 8 - Character.getNumericValue(parts[0].charAt(1));
        int fromCol = parts[0].charAt(0) - 'A';
        int toRow = 8 - Character.getNumericValue(parts[1].charAt(1));
        int toCol = parts[1].charAt(0) - 'A';

        char piece = board[fromRow][fromCol];
        if (piece == ' ' || (Character.isUpperCase(piece) && playerColor == 'B')
                || (Character.isLowerCase(piece) && playerColor == 'W')) {
            return false;
        }

        Piece p = pieceMap.get(piece);
        if (p != null && p.isValidMove(fromRow, fromCol, toRow, toCol, this)) {
            if (board[toRow][toCol] != ' '
                    && Character.isUpperCase(board[toRow][toCol]) == Character.isUpperCase(piece)) {
                return false;
            }
            board[toRow][toCol] = piece;
            board[fromRow][fromCol] = ' ';
            return true;
        }
        return false;
    }

    public boolean isCaptureMoveAvailable(char playerColor) {
        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                char piece = board[fromRow][fromCol];
                if (piece == ' ' || (Character.isUpperCase(piece) && playerColor == 'B')
                        || (Character.isLowerCase(piece) && playerColor == 'W')) {
                    continue;
                }

                Piece p = pieceMap.get(piece);
                for (int toRow = 0; toRow < 8; toRow++) {
                    for (int toCol = 0; toCol < 8; toCol++) {
                        if (board[toRow][toCol] != ' '
                                && Character.isUpperCase(board[toRow][toCol]) != Character.isUpperCase(piece)) {
                            if (p.isValidMove(fromRow, fromCol, toRow, toCol, this)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean hasOnlyKing(char playerColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                char piece = board[row][col];
                if (playerColor == 'W' && Character.isUpperCase(piece) && piece != '\u2654') {
                    return false;
                } else if (playerColor == 'B' && Character.isLowerCase(piece) && piece != '\u265A') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin() {
        return hasOnlyKing('W') || hasOnlyKing('B');
    }

    // @Override
    // public String toString() {
    // String boardString = " A B C D E F G H\n";
    // for (int i = 0; i < 8; i++) {
    // System.out.print(8 - i + " ");
    // for (int j = 0; j < 8; j++) {
    // System.out.print("| ");
    // System.out.print(board[i][j] + " ");
    // }
    // System.out.println("| " + (8 - i));
    // System.out.println(" ------------------------------------");
    // }
    // System.out.println(" ......................................\n\n");
    // System.out.println(" A B C D E F G H");

    // return boardString;
    // }

    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("    A   B   C   D   E   F   G   H\n");
        for (int i = 0; i < 8; i++) {
            boardString.append(8 - i).append(" ");
            for (int j = 0; j < 8; j++) {
                boardString.append("| ").append(board[i][j]).append(" ");
            }
            boardString.append("| ").append(8 - i).append("\n");
            boardString.append("  ------------------------------------\n");
        }
        boardString.append("    A   B   C   D   E   F   G   H\n");

        return boardString.toString();
    }

    public char getPiece(int row, int col) {
        return board[row][col];
    }
}

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
        // Place pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = 'P'; // for White pawns
            board[6][i] = 'p'; // for Black pawns
        }

        // bishops
        board[0][2] = board[0][5] = 'B'; // White bishops
        board[7][2] = board[7][5] = 'b'; // Black bishops

        // rooks
        board[0][0] = board[0][7] = 'R'; // White rooks
        board[7][0] = board[7][7] = 'r'; // Black rooks

        // knights
        board[0][1] = board[0][6] = 'N'; // White knights
        board[7][1] = board[7][6] = 'n'; // Black knights

        // queens
        board[0][3] = 'Q'; // White queen
        board[7][3] = 'q'; // Black queen

        // kings
        board[0][4] = 'K'; // White king
        board[7][4] = 'k'; // Black king

        // to fill in empty spaces
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = ' ';
            }
        }
        // to put the piece in Map
        pieceMap.put('P', new Pawn('W'));
        pieceMap.put('R', new Rook('W'));
        pieceMap.put('N', new Knight('W'));
        pieceMap.put('B', new Bishop('W'));
        pieceMap.put('Q', new Queen('W'));
        pieceMap.put('K', new King('W'));
        pieceMap.put('p', new Pawn('B'));
        pieceMap.put('r', new Rook('B'));
        pieceMap.put('n', new Knight('B'));
        pieceMap.put('b', new Bishop('B'));
        pieceMap.put('q', new Queen('B'));
        pieceMap.put('k', new King('B'));
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
                if (playerColor == 'W' && Character.isUpperCase(piece) && piece != 'K') {
                    return false;
                } else if (playerColor == 'B' && Character.isLowerCase(piece) && piece != 'k') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin() {
        return hasOnlyKing('W') || hasOnlyKing('B');
    }

    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("   A B C D E F G H\n");
        for (int i = 0; i < 8; i++) {
            boardString.append(8 - i).append(" ");
            for (int j = 0; j < 8; j++) {
                boardString.append("|").append(board[i][j]);
            }
            boardString.append("| ").append(8 - i).append("\n");
            boardString.append(" ---------------------\n");
        }
        boardString.append("    A B C D E F G H\n");

        return boardString.toString();
    }

    public char getPiece(int row, int col) {
        return board[row][col];
    }

    public Map<Character, Piece> getPieceMap() {
        return pieceMap;
    }
}

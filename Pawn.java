public class Pawn extends Piece {
    public Pawn(char color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Board board) {
        if (color == 'W') {
            if (fromRow == 1) {
                if (toRow == fromRow + 2 && fromCol == toCol && board.getPiece(toRow, toCol) == ' ') {
                    return true;
                }
            }
            if (toRow == fromRow + 1 && fromCol == toCol && board.getPiece(toRow, toCol) == ' ') {
                return true;
            }
            if (toRow == fromRow + 1 && Math.abs(fromCol - toCol) == 1 && board.getPiece(toRow, toCol) != ' '
                    && Character.isLowerCase(board.getPiece(toRow, toCol))) {
                return true;
            }
        } else {
            if (fromRow == 6) {
                if (toRow == fromRow - 2 && fromCol == toCol && board.getPiece(toRow, toCol) == ' ') {
                    return true;
                }
            }
            if (toRow == fromRow - 1 && fromCol == toCol && board.getPiece(toRow, toCol) == ' ') {
                return true;
            }
            if (toRow == fromRow - 1 && Math.abs(fromCol - toCol) == 1 && board.getPiece(toRow, toCol) != ' '
                    && Character.isUpperCase(board.getPiece(toRow, toCol))) {
                return true;
            }
        }
        return false;
    }
}

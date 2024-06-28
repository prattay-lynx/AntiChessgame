public class Bishop extends Piece {
    public Bishop(char color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Board board) {
        if (Math.abs(fromRow - toRow) != Math.abs(fromCol - toCol)) {
            return false;
        }

        int stepRow = Integer.compare(toRow, fromRow);
        int stepCol = Integer.compare(toCol, fromCol);

        int row = fromRow + stepRow;
        int col = fromCol + stepCol;

        while (row != toRow || col != toCol) {
            if (board.getPiece(row, col) != ' ') {
                return false;
            }
            row += stepRow;
            col += stepCol;
        }

        return true;
    }
}

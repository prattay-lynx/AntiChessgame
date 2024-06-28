public class Queen extends Piece {
    public Queen(char color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Board board) {
        if (fromRow == toRow || fromCol == toCol) {
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

        if (Math.abs(fromRow - toRow) == Math.abs(fromCol - toCol)) {
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

        return false;
    }
}

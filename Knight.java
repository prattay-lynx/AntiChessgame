public class Knight extends Piece {
    public Knight(char color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Board board) {
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}

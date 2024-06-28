public abstract class Piece {
    // For color convention: 'W' for white, 'B' for black
    protected char color;

    public Piece(char color) {
        this.color = color;
    }

    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Board board);
}

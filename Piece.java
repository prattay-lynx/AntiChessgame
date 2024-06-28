public abstract class Piece {
    protected char color; // 'W' for white, 'B' for black

    public Piece(char color) {
        this.color = color;
    }

    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Board board);
}

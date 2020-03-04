public enum Move {
    LEFT(0, -1),
    RIGHT(0, 1),
    UP(-1, 0),
    DOWN(1, 0);

    public final int dx, dy;
    Move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

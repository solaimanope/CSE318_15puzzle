public class Grid {
    private int[][] grid;
    private Long bitrep;
    private int _x, _y;

    Grid(int[][] grid) {
        this.grid = new int[4][4];
        bitrep = 0L;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.grid[i][j] = grid[i][j];
                bitrep = (bitrep<<4)|grid[i][j];
                if (grid[i][j] == 0) {
                    _x = i;
                    _y = j;
                }
            }
        }
    }

    @Override
    public int hashCode() {
        return bitrep.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Grid)) return false;
        return bitrep.longValue() == ((Grid) obj).bitrep.longValue();
    }
}

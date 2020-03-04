public class Grid {
    private int[][] grid;
    private Long bitrep;
    private int _x, _y;
    private Grid parent;

    public Grid(int[][] grid) {
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
        parent = null;
    }

    public int[][] getGrid() {
        return grid;
    }

    public Grid getParent() {
        return parent;
    }

    private int displacementHeuristic(Grid goal) {
        int h = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 0) continue;
                if (goal.grid[i][j] != grid[i][j]) h++;
            }
        }
        return h;
    }
    private int manhattanHeuristic(Grid goal) {
        int[] rx = new int[16];
        int[] ry = new int[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rx[goal.grid[i][j]] = i;
                ry[goal.grid[i][j]] = j;
            }
        }

        int h = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid[i][j] == 0) continue;
                h += Math.abs(i-rx[grid[i][j]])+Math.abs(j-ry[grid[i][j]]);
            }
        }
        return h;
    }

    public int heuristic(Grid goal, int type) {
        if (type == 1) return displacementHeuristic(goal);
        if (type == 2) return manhattanHeuristic(goal);
        return 0;
    }

    public Grid makeMove(Move move) {
        int __x =  _x+move.dx;
        int __y =  _y+move.dy;
        if (__x < 0 || __y < 0 || __x >= 4 || __y >= 4) return null;

        int[][] tmp = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tmp[i][j] = grid[i][j];
            }
        }

        tmp[_x][_y] = tmp[__x][__y];
        tmp[__x][__y] = 0;

        Grid g = new Grid(tmp);
        g.parent = this;
        return g;
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

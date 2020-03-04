import java.util.Comparator;

public class Node {
    private Grid grid;
    private int g, h;

    public Node(Grid grid, int g, int h) {
        this.grid = grid;
        this.g = g;
        this.h = h;
    }

    public int f() {
        return g+h;
    }
    public int g() {
        return g;
    }
    public int h() {
        return h;
    }
    public Grid getGrid() {
        return grid;
    }
}

class NodeComparator implements Comparator<Node> {
    public int compare(Node n1, Node n2) {
        return n1.f()-n2.f();
    }
}
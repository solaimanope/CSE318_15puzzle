import java.io.File;
import java.util.*;

public class Main {
    private static int parity(int[][] grid) {
        int[] ar = new int[16];

        int ret = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ar[i*4+j] = grid[i][j];
                if (grid[i][j] == 0) {
                    ret ^= (i+1)&1;
                }
            }
        }

        for (int i = 0; i < 16; i++) {
            if (ar[i] == 0) continue;
            for (int j = i+1; j < 16; j++) {
                if (ar[j] == 0) continue;
                if (ar[i] > ar[j]) ret ^= 1;
            }
        }

        return ret;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(new File("src/input.txt"));
        int testCases = scanner.nextInt();

        int[][] goal = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                goal[i][j] = scanner.nextInt();
            }
        }

        for (int ti = 1; ti < testCases; ti++) {
            if (ti > 1) System.out.println();
            System.out.println("Solving Initial State #" + ti);
            int[][] start = new int[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    start[i][j] = scanner.nextInt();
                }
            }

            if (parity(start) != parity(goal)) {
                System.out.println("No solution");
                return;
            }

            Grid startGrid = new Grid(start);
            Grid goalGrid = new Grid(goal);

            System.out.println();
            AStarSolver(startGrid, goalGrid, 1);
            System.out.println();
            AStarSolver(startGrid, goalGrid, 2);
        }
    }

    // heuristicType = 1 for displacementHeuristic()
    // heuristicType = 2 for manhattanHeuristic()
    private static void AStarSolver(Grid startGrid, Grid goalGrid, int heuristicType) {
        long startTime = System.currentTimeMillis();

        Map<Grid, Integer>map = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());

        pq.add(new Node(startGrid, 0, startGrid.heuristic(goalGrid, heuristicType)));
        map.put(startGrid, 0);

        Grid finalGrid;
        int poppedElementCount = 0;

        while (true) {
            Node u = pq.poll();
            poppedElementCount++;
            if (u.getGrid().equals(goalGrid)) {
                finalGrid = u.getGrid();
                break;
            }

            if (map.get(u.getGrid()) != u.g()) continue;

            for (Move move : Move.values()) {
                Grid gv = u.getGrid().makeMove(move);
                if (gv == null) continue; //invalid move, when empty block is on side

                Integer I = map.get(gv);
                if (I == null || I > u.g()+1) {
                    map.put(gv, u.g()+1);
                    pq.add(new Node(gv, u.g()+1, gv.heuristic(goalGrid, heuristicType)));
                }
            }
        }

        if (finalGrid == null) {
            System.out.println("Something's wrong. I can feel it.");
            return;
        }

        long finishTime = System.currentTimeMillis();

        ArrayList<Grid>path = new ArrayList<>();
        while (finalGrid != null) {
            path.add(finalGrid);
            finalGrid = finalGrid.getParent();
        }

        Collections.reverse(path);

        if (heuristicType == 1) System.out.println("Solution using displacement heuristic");
        else if (heuristicType == 2) System.out.println("Solution using manhattan heuristic");

        for (int i = 0; i < path.size(); i++) {
            if (i > 0) {
                Move move = path.get(i).getLastMove();
                if (move == Move.LEFT)          System.out.println("--Right--");
                else if (move == Move.RIGHT)    System.out.println("--Left---");
                else if (move == Move.DOWN)     System.out.println("---Up----");
                else if (move == Move.UP)       System.out.println("--Down---");
            }
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    System.out.print(path.get(i).getGrid()[x][y] + " ");
                }
                System.out.println();
            }
        }

        System.out.println("\nMinimum Steps: " + (path.size()-1));
        System.out.println("Time taken : " + (finishTime-startTime) + " milliseconds");
        System.out.println("Nodes expanded: " + poppedElementCount);
    }
}
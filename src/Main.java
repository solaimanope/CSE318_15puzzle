import java.util.Scanner;

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter initial state");
        int[][] start = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                start[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Please enter goal state");
        int[][] goal = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                goal[i][j] = scanner.nextInt();
            }
        }

        if (parity(start) != parity(goal)) {
            System.out.println("No solution");
            return;
        }

        System.out.println("Solution exists");
    }
}

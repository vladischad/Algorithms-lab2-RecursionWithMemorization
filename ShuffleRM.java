/**
 * ShuffleRM.java
 * Determines if Z is a shuffle of X and Y using top-down recursion with memoization.
 * Author: Vladyslav (Vlad) Maliutin
 */

import java.io.*;

public class ShuffleRM {
    static int[][] memo;
    static String X, Y, Z;
    static int tableRefs = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java ShuffleRM <X> <Y> <Z> [<debug level>]");
            return;
        }

        X = args[0];
        Y = args[1];
        Z = args[2];
        int debug = (args.length == 4) ? Integer.parseInt(args[3]) : 0;

        if (X.length() + Y.length() != Z.length()) {
            System.out.println("Usage: java ShuffleRM <X> <Y> <Z> [<debug level>]");
            return;
        }

        int m = X.length();
        int n = Y.length();
        memo = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++)
            for (int j = 0; j <= n; j++)
                memo[i][j] = -1;

        boolean result = isShuffle(m, n);

        if (debug == 1) {
            try (PrintWriter out = new PrintWriter("ShuffleRM-Table")) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        out.print((memo[i][j]) + (j == n ? "\n" : "  "));
                    }
                }
            }
        }

        if (debug == 1) {
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    System.out.print(String.format("%2d", memo[i][j]) + (j == n ? "\n" : "  "));
                }
            }
        }
    

        System.out.println(result ? "yes" : "no");
        System.out.println("Number of table references: " + tableRefs);
    }

    // Top-down recursive method with memoization
    public static boolean isShuffle(int i, int j) {
        tableRefs++;

        if (memo[i][j] != -1) {
            return memo[i][j] == 1;
        }

        if (i == 0 && j == 0) {
            memo[i][j] = 1;
            return true;
        }

        int k = i + j - 1;
        boolean valid = false;

        if (i > 0 && X.charAt(i - 1) == Z.charAt(k) && isShuffle(i - 1, j)) {
            valid = true;
        }
        if (!valid && j > 0 && Y.charAt(j - 1) == Z.charAt(k) && isShuffle(i, j - 1)) {
            valid = true;
        }

        memo[i][j] = valid ? 1 : 0;
        return valid;
    }
}

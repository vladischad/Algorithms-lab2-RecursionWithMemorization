/**
 * ShuffleDP.java
 * Determines if a string Z is a valid shuffle of strings X and Y using bottom-up DP.
 * Author: Vladyslav (Vlad) Maliutin
 */

import java.io.*;

public class ShuffleDP {
    public static void main(String[] args) throws IOException {
        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java ShuffleDP <X> <Y> <Z> [<debug level>]");
            return;
        }

        String X = args[0];
        String Y = args[1];
        String Z = args[2];
        int debug = (args.length == 4) ? Integer.parseInt(args[3]) : 0;

        if (X.length() + Y.length() != Z.length()) {
            System.out.println("Usage: java ShuffleDP <X> <Y> <Z> [<debug level>]");
            return;
        }

        int m = X.length();
        int n = Y.length();
        boolean[][] table = new boolean[m + 1][n + 1];
        int tableRefs = 0;

        table[0][0] = true;

        for (int i = 1; i <= m; i++) {
            table[i][0] = table[i - 1][0] && (X.charAt(i - 1) == Z.charAt(i - 1));
            tableRefs++;
        }

        for (int j = 1; j <= n; j++) {
            table[0][j] = table[0][j - 1] && (Y.charAt(j - 1) == Z.charAt(j - 1));
            // tableRefs++;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char zChar = Z.charAt(i + j - 1);
                table[i][j] = (X.charAt(i - 1) == zChar && table[i - 1][j]) ||
                            (Y.charAt(j - 1) == zChar && table[i][j - 1]);
                tableRefs++;
            }
        }

        if (debug == 1) {
            try (PrintWriter out = new PrintWriter("ShuffleDP-Table")) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        out.print((table[i][j] ? 1 : 0) + (j == n ? "\n" : "  "));
                    }
                }
            }
        }

        if (debug == 1) {
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    System.out.print((table[i][j] ? 1 : 0) + (j == n ? "\n" : "  "));
                }
            }
        }

        System.out.println(table[m][n] ? "yes" : "no");
        System.out.println("Number of table references: " + tableRefs);
    }
}

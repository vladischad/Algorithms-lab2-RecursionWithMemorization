import java.io.*;
import java.util.*;

public class KnapsackRM {
    static int[][] memo, decision;
    static int[] weights, values;
    static int refs = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 4 || args.length > 5) {
            System.out.println("Usage: java KnapsackRM <n> <W> <w.txt> <v.txt> [<debug level>]");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int W = Integer.parseInt(args[1]);
        weights = readFile(args[2], n);
        values = readFile(args[3], n);
        int debug = args.length == 5 ? Integer.parseInt(args[4]) : 0;

        memo = new int[n + 1][W + 1];
        decision = new int[n + 1][W + 1];
        for (int[] row : memo) Arrays.fill(row, -1);

        int optimal = knapsack(n, W);
        System.out.println("Optimal value: " + optimal);
        System.out.println("Table references: " + refs);

        if (debug == 1) {
            writeTable("KnapsackRM-VTable", memo);
            writeTable("KnapsackRM-DTable", decision);
        }
    }

    static int knapsack(int i, int w) {
        refs++;
        if (i == 0 || w == 0) return 0;
        if (memo[i][w] != -1) return memo[i][w];

        if (weights[i - 1] > w) {
            memo[i][w] = knapsack(i - 1, w);
        } else {
            int exclude = knapsack(i - 1, w);
            int include = values[i - 1] + knapsack(i - 1, w - weights[i - 1]);
            if (include > exclude) {
                memo[i][w] = include;
                decision[i][w] = 1;
            } else {
                memo[i][w] = exclude;
            }
        }
        return memo[i][w];
    }

    private static int[] readFile(String filename, int n) throws IOException {
        int[] arr = new int[n];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(br.readLine().trim());
            }
        }
        return arr;
    }

    private static void writeTable(String filename, int[][] table) throws IOException {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (int[] row : table) {
                for (int val : row) {
                    out.print(val + " ");
                }
                out.println();
            }
        }
    }
}

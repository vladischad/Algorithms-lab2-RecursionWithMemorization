import java.io.*;

public class KnapsackDP {
    public static void main(String[] args) throws IOException {
        if (args.length < 4 || args.length > 5) {
            System.out.println("Usage: java KnapsackDP <n> <W> <w.txt> <v.txt> [<debug level>]");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int W = Integer.parseInt(args[1]);
        int[] weights = readFile(args[2], n);
        int[] values = readFile(args[3], n);
        int debug = args.length == 5 ? Integer.parseInt(args[4]) : 0;

        int[][] dp = new int[n + 1][W + 1];
        int[][] decision = new int[n + 1][W + 1];
        int refs = 0;

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (weights[i - 1] <= w) {
                    int include = values[i - 1] + dp[i - 1][w - weights[i - 1]];
                    int exclude = dp[i - 1][w];
                    if (include > exclude) {
                        dp[i][w] = include;
                        decision[i][w] = 1;
                    } else {
                        dp[i][w] = exclude;
                    }
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
                refs++;
            }
        }

        System.out.println("Optimal value: " + dp[n][W]);
        System.out.println("Table references: " + refs);

        if (debug == 1) {
            writeTable("KnapsackDP-VTable", dp);
            writeTable("KnapsackDP-DTable", decision);
        }
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
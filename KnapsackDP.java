/**
 * KnapsackDP.java
 * Solves the 0-1 Knapsack problem using bottom-up dynamic programming.
 * Prints value and decision tables to the console when debug = 1.
 * Author: Vladyslav (Vlad) Maliutin
 */

import java.io.*;
import java.util.*;

public class KnapsackDP {
    public static void main(String[] args) throws IOException {
        if (args.length < 4 || args.length > 5) {
            System.out.println("Usage: java KnapsackDP <n> <W> <w.txt> <v.txt> [<debug level>]");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int W = Integer.parseInt(args[1]);
        String wFile = args[2];
        String vFile = args[3];
        int debug = (args.length == 5) ? Integer.parseInt(args[4]) : 0;

        int[] weights = readFile(wFile, n);
        int[] values = readFile(vFile, n);

        int[][] V = new int[n + 1][W + 1];
        int[][] D = new int[n + 1][W + 1];
        int tableRefs = 0;

        // DP table construction
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                tableRefs++;
                if (weights[i - 1] <= w) {
                    int include = values[i - 1] + V[i - 1][w - weights[i - 1]];
                    int exclude = V[i - 1][w];
                    if (include > exclude) {
                        V[i][w] = include;
                        D[i][w] = 1;
                    } else {
                        V[i][w] = exclude;
                        D[i][w] = 0;
                    }
                } else {
                    V[i][w] = V[i - 1][w];
                    D[i][w] = 0;
                }
            }
        }

        // If debug level 1, print the tables
        if (debug == 1) {
            System.out.println("KnapsackDP-VTable:");
            printTable(V);
            System.out.println("\nKnapsackDP-DTable:");
            printTable(D);
        }

        // Reconstruct optimal solution
        List<Integer> solution = new ArrayList<>();
        int w = W;
        for (int i = n; i > 0; i--) {
            if (D[i][w] == 1) {
                solution.add(i);
                w -= weights[i - 1];
            }
        }

        Collections.sort(solution);
        int totalValue = V[n][W];
        int totalWeight = 0;
        for (int idx : solution) {
            totalWeight += weights[idx - 1];
        }

        // Print results
        System.out.println("\nOptimal solution:");
        System.out.println(solution);
        System.out.println("Total Weight: " + totalWeight);
        System.out.println("Optimal Value: " + totalValue);
        System.out.println("Number of table references: " + tableRefs);
    }

    // Helper to read a file of n integers
    private static int[] readFile(String filename, int n) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        return arr;
    }

    // Helper to print table with aligned spacing
    private static void printTable(int[][] table) {
        for (int i = 1; i < table.length; i++) { // start from 1, skip first row
            for (int j = 1; j < table[i].length; j++) {
                int val = table[i][j];
                if (val < 10) {
                    System.out.print(val + "   "); // 3 spaces
                } else if (val < 100) {
                    System.out.print(val + "  ");  // 2 spaces
                } else {
                    System.out.print(val + " ");   // 1 space
                }
            }
            System.out.println();
        }
    }
}

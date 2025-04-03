/**
 * KnapsackRM.java
 * Solves the 0-1 Knapsack problem using recursion with memoization (top-down DP).
 * Author: Vladyslav (Vlad) Maliutin
 */

 import java.io.*;
 import java.util.*;
 
 public class KnapsackRM {
     static int[][] memo, decision;
     static int[] weights, values;
     static int tableRefs = 0;
 
     public static void main(String[] args) throws IOException {
         if (args.length < 4 || args.length > 5) {
             System.out.println("Usage: java KnapsackRM <n> <W> <w.txt> <v.txt> [<debug level>]");
             return;
         }
 
         int n = Integer.parseInt(args[0]);
         int W = Integer.parseInt(args[1]);
         int debug = (args.length == 5) ? Integer.parseInt(args[4]) : 0;
 
         weights = readFile(args[2], n);
         values = readFile(args[3], n);
 
         memo = new int[n + 1][W + 1];
         decision = new int[n + 1][W + 1];
 
         for (int[] row : memo) Arrays.fill(row, -1);
 
         int maxValue = solve(n, W);
 
         if (debug == 1) {
             System.out.println("KnapsackRM-VTable:");
             printTable(memo);
             System.out.println("\nKnapsackRM-DTable:");
             printTable(decision);
         }
 
         List<Integer> solution = new ArrayList<>();
         int w = W;
         for (int i = n; i > 0; i--) {
             if (decision[i][w] == 1) {
                 solution.add(i);
                 w -= weights[i - 1];
             }
         }
         Collections.sort(solution);
 
         int totalWeight = 0;
         for (int idx : solution) totalWeight += weights[idx - 1];
 
         System.out.println("Optimal solution:");
         System.out.println(solution);
         System.out.println("Total Weight: " + totalWeight);
         System.out.println("Optimall Value: " + maxValue);
         System.out.println("Number of table references: " + tableRefs);
     }
 
     static int solve(int i, int w) {
         tableRefs++;
         if (i == 0 || w == 0) return 0;
         if (memo[i][w] != -1) return memo[i][w];
 
         if (weights[i - 1] > w) {
             decision[i][w] = 0;
             return memo[i][w] = solve(i - 1, w);
         } else {
             int exclude = solve(i - 1, w);
             int include = values[i - 1] + solve(i - 1, w - weights[i - 1]);
             if (include > exclude) {
                 decision[i][w] = 1;
                 return memo[i][w] = include;
             } else {
                 decision[i][w] = 0;
                 return memo[i][w] = exclude;
             }
         }
     }
 
     private static int[] readFile(String filename, int n) throws IOException {
         BufferedReader br = new BufferedReader(new FileReader(filename));
         int[] arr = new int[n];
         for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(br.readLine());
         return arr;
     }
 
     private static void printTable(int[][] table) {
         for (int i = 1; i < table.length; i++) {
             for (int j = 0; j < table[i].length; j++) {
                 int val = table[i][j];
                 if (val < 10 && val >= 0) {
                     System.out.print(val + "   ");
                 } else if (val < 100 && val >= 0) {
                     System.out.print(val + "  ");
                 } else if (val >= 0) {
                     System.out.print(val + " ");
                 } else {
                     System.out.print(val + "  "); // -1 default spacing
                 }
             }
             System.out.println();
         }
     }
 }
 
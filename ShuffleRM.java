import java.io.*;

public class ShuffleRM {
    static Boolean[][] memo;
    static String X, Y, Z;
    static int refs = 0;

    public static void main(String[] args) throws IOException {
        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java ShuffleRM <X> <Y> <Z> [<debug level>]");
            return;
        }

        X = args[0]; Y = args[1]; Z = args[2];
        int debug = args.length == 4 ? Integer.parseInt(args[3]) : 0;

        if (X.length() + Y.length() != Z.length()) {
            System.out.println("Incompatible input lengths.");
            return;
        }

        memo = new Boolean[X.length() + 1][Y.length() + 1];
        boolean result = shuffle(X.length(), Y.length());

        System.out.println(result ? "yes" : "no");
        System.out.println("Table references: " + refs);

        if (debug == 1) {
            try (PrintWriter out = new PrintWriter("ShuffleRM-Table")) {
                for (int i = 0; i <= X.length(); i++) {
                    for (int j = 0; j <= Y.length(); j++) {
                        if (memo[i][j] == null) out.print("-1 ");
                        else out.print((memo[i][j] ? 1 : 0) + " ");
                    }
                    out.println();
                }
            }
        }
    }

    static boolean shuffle(int i, int j) {
        refs++;
        if (i == 0 && j == 0) return true;
        if (memo[i][j] != null) return memo[i][j];

        boolean res = false;
        if (i > 0 && X.charAt(i - 1) == Z.charAt(i + j - 1)) res |= shuffle(i - 1, j);
        if (j > 0 && Y.charAt(j - 1) == Z.charAt(i + j - 1)) res |= shuffle(i, j - 1);

        return memo[i][j] = res;
    }
}

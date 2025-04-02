import java.io.*;

public class ShuffleDP {
    public static void main(String[] args) throws IOException {
        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java ShuffleDP <X> <Y> <Z> [<debug level>]");
            return;
        }

        String X = args[0], Y = args[1], Z = args[2];
        int debug = args.length == 4 ? Integer.parseInt(args[3]) : 0;

        if (X.length() + Y.length() != Z.length()) {
            System.out.println("Incompatible input lengths.");
            return;
        }

        boolean[][] table = new boolean[X.length() + 1][Y.length() + 1];
        int refs = 0;

        table[0][0] = true;
        for (int i = 1; i <= X.length(); i++) {
            table[i][0] = table[i - 1][0] && X.charAt(i - 1) == Z.charAt(i - 1);
            refs++;
        }
        for (int j = 1; j <= Y.length(); j++) {
            table[0][j] = table[0][j - 1] && Y.charAt(j - 1) == Z.charAt(j - 1);
            refs++;
        }

        for (int i = 1; i <= X.length(); i++) {
            for (int j = 1; j <= Y.length(); j++) {
                table[i][j] = (X.charAt(i - 1) == Z.charAt(i + j - 1) && table[i - 1][j]) ||
                              (Y.charAt(j - 1) == Z.charAt(i + j - 1) && table[i][j - 1]);
                refs += 2;
            }
        }

        System.out.println(table[X.length()][Y.length()] ? "yes" : "no");
        System.out.println("Table references: " + refs);

        if (debug == 1) {
            try (PrintWriter out = new PrintWriter("ShuffleDP-Table")) {
                for (int i = 0; i <= X.length(); i++) {
                    for (int j = 0; j <= Y.length(); j++) {
                        out.print((table[i][j] ? 1 : 0) + " ");
                    }
                    out.println();
                }
            }
        }
    }
}
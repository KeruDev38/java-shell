import shell.ShellContext;
import shell.ShellManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ShellContext ctx = new ShellContext();

        ShellManager manager = new ShellManager(sc, ctx);
        manager.begin();
    }
}

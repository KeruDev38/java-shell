import shell.ShellCommand;
import shell.ShellManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ShellManager manager = new ShellManager(new Scanner(System.in));
        // TODO hashmap with lambdas
        manager.begin();
    }
}

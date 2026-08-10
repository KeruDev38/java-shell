package shell;

import shell.exception.IncorrectContructionException;

import java.util.Scanner;

public class ShellManager {
    private final Scanner sc;
    private final ShellExecutor executor;

    public ShellManager(Scanner sc) {
        this.sc = sc;
        this.executor = new ShellExecutor();
    }

    public void begin() {
        checkIntegrity();

        while(executor.isRunning()) {
            ShellCommand input = readCommand();
            executor.execute(input);
        }
    }

    private ShellCommand readCommand() {
        System.out.print("$ ");
        String[] splitted = sc.nextLine().trim().split("\\s+", 2);
        return new ShellCommand(
                splitted[0],
                splitted.length > 1 ? splitted[1] : ""
        );
    }

    private void checkIntegrity() {
        if (sc == null) throw new IncorrectContructionException("Scanner cannot be null.");
    }
}

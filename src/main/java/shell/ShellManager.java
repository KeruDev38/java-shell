package shell;

import shell.command.ShellInput;
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

        boolean running = true;
        while(running) {
            ShellInput input = readCommand();
            running = executor.execute(input);
        }
    }

    private ShellInput readCommand() {
        System.out.print("$ ");
        String[] splitted = sc.nextLine().trim().split("\\s+", 2);
        return new ShellInput(
                splitted[0],
                splitted.length > 1 ? splitted[1] : ""
        );
    }

    private void checkIntegrity() {
        if (sc == null) throw new IncorrectContructionException("Scanner cannot be null.");
    }
}

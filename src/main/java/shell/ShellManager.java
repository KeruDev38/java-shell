package shell;

import shell.command.ShellInput;
import shell.exception.IncorrectContructionException;

import java.util.Scanner;

public class ShellManager {
    private final Scanner sc;
    private final ShellExecutor executor;
    private final ShellContext context;

    public ShellManager(Scanner sc) {
        this.sc = sc;
        this.executor = new ShellExecutor();
        this.context = new ShellContext();
    }

    public void begin() {
        checkIntegrity();

        while(context.isRunning()) {
            ShellInput input = readCommand();
            executor.execute(input);
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

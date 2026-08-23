package shell;

import shell.command.ShellInput;
import shell.exception.IncorrectConstructionException;

import java.util.Scanner;

public class ShellManager {
    private final Scanner sc;
    private final ShellExecutor executor;

    public ShellManager(Scanner sc, ShellContext ctx) {
        this.sc = sc;
        ExecutableResolver resolver = new ExecutableResolver(ctx);
        this.executor = new ShellExecutor(
                new CommandService(ctx, resolver),
                resolver,
                new ProcessExecutor()
        );
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
        if (sc == null) throw new IncorrectConstructionException("Scanner cannot be null.");
    }
}

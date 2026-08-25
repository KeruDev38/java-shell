package shell;

import shell.command.CommandService;
import shell.reader.CommandReader;
import shell.reader.ShellInput;
import shell.exception.IncorrectConstructionException;

import java.util.Scanner;

public class ShellManager {
    private final Scanner sc;
    private final ShellExecutor executor;

    public ShellManager(Scanner sc, ShellContext ctx) {
        this.sc = sc;
        DirectoryResolver resolver = new DirectoryResolver(ctx);
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
            ShellInput input = CommandReader.readCommand();
            running = executor.execute(input);
        }
    }

    private void checkIntegrity() {
        if (sc == null) throw new IncorrectConstructionException("Scanner cannot be null.");
    }
}

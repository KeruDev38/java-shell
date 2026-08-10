package shell;

import shell.exception.IncorrectContructionException;

import java.util.Scanner;

public class ShellManager {
    private final Scanner sc;
    private final ShellAnswer answer;

    public ShellManager(Scanner sc) {
        this.sc = sc;
        this.answer = new ShellAnswer();
    }

    public void begin() {
        checkIntegrity();

        while(true) {
            ShellCommand input = readCommand();
            String command = input.getCommand();

            if (command.equals("exit")) break;
            else if (command.equals("echo")) {
                answer.echo(input.getArguments());
            }
            else answer.commandNotFound(command);
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

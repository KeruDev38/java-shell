package shell.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandReader {
    private static final Scanner sc = new Scanner(System.in);

    public static ShellInput readCommand() {
        System.out.print("$ ");

        StringBuilder command = new StringBuilder(sc.nextLine());

        ParseResult result = CommandParser.parse(command);

        while (!result.isComplete()) {
            System.out.print(">> ");

            command.append("\n")
                    .append(sc.nextLine());

            result = CommandParser.parse(command);
        }

        return ShellInput.from(result);
    }
}

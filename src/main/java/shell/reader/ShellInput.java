package shell.reader;

import java.util.List;

public class ShellInput {
    private final String command;
    private final List<String> arguments;

    public ShellInput(String command, List<String> arguments) {
        this.command = command;
        this.arguments = List.copyOf(arguments);
    }

    public static ShellInput from(ParseResult result) {
        List<String> tokens = result.getTokens();

        if (tokens.isEmpty()) {
            return new ShellInput("", List.of());
        }

        return new ShellInput(
                tokens.getFirst(),
                tokens.subList(1, tokens.size())
        );
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }
}

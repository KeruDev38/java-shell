package shell.command;

public class ShellInput {
    private final String command;
    private final String arguments;

    public ShellInput(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public String getArguments() {
        return arguments;
    }
}

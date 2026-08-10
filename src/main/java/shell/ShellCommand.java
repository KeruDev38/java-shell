package shell;

public class ShellCommand {
    private final String command;
    private final String arguments;

    public ShellCommand(String command, String arguments) {
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

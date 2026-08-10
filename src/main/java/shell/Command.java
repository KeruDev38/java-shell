package shell;

public enum Command {
    EXIT("exit"),
    ECHO("echo"),
    TYPE("type");

    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Command fromString(String text) {
        for (Command command : Command.values()) {
            if(command.name.equalsIgnoreCase(text)) {
                return command;
            }
        }
        return null;
    }
}

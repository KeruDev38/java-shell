package shell.command;

public enum CommandType {
    EXIT("exit"),
    ECHO("echo"),
    TYPE("type");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CommandType fromString(String text) {
        for (CommandType commandType : CommandType.values()) {
            if(commandType.name.equalsIgnoreCase(text)) {
                return commandType;
            }
        }
        return null;
    }
}

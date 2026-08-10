package shell;

import shell.command.Execution;
import shell.command.CommandType;
import shell.command.ShellInput;

import java.util.EnumMap;
import java.util.Map;

public class ShellExecutor {
    private final Map<CommandType, Execution> commandsMap = new EnumMap<>(CommandType.class);

    public ShellExecutor() {
        commandsMap.put(CommandType.ECHO, this::echo);
        commandsMap.put(CommandType.TYPE, this::type);
        commandsMap.put(CommandType.EXIT, this::exit);
    }

    public boolean execute(ShellInput input) {
        CommandType type = CommandType.fromString(input.getCommand());

        if(type != null && commandsMap.containsKey(type)) {
            return commandsMap.get(type).execute(input);
        } else {
            commandNotFound(input.getCommand());
            return true;
        }
    }

    private boolean echo(ShellInput input) {
        System.out.println(input.getArguments());
        return true;
    }

    private boolean type(ShellInput input) {
        String targetStr = input.getCommand();
        CommandType target = CommandType.fromString(targetStr);

        if (target != null) {
            System.out.println(input.getArguments() + " is a shell builtin");
        } else {
            commandNotFound(targetStr);
        }
        return true;
    }

    private boolean exit(ShellInput input) {
        return false;
    }

    private void commandNotFound(String command) {
        System.out.println(command + ": command not found");
    }
}

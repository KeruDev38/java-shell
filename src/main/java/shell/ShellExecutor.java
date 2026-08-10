package shell;

import shell.command.Execution;
import shell.command.CommandType;
import shell.command.ShellInput;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

public class ShellExecutor {
    private final ShellContext ctx = new ShellContext();
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
        String targetStr = input.getArguments().trim();
        CommandType target = CommandType.fromString(targetStr);

        if (target != null) {
            System.out.println(input.getArguments() + " is a shell builtin");
            return true;

        } else if (ctx.getEnv() != null && !ctx.getEnv().isEmpty()) {
            String fullPath = findExecutable(targetStr);
            if (fullPath != null) {
                System.out.println(targetStr + " is " + fullPath);
            }

        }

        System.out.println(targetStr + ": not found");
        return true;
    }

    private String findExecutable(String target) {
        for (String route : ctx.getEnv().split(File.pathSeparator)) {
            Path fullPath = Paths.get(route, target);
            String fileName = fullPath.getFileName().toString();

            if (Files.isExecutable(fullPath)) {
                return fullPath.toString();
            }
        }
        return null;
    }

    private boolean exit(ShellInput input) {
        return false;
    }

    private void commandNotFound(String command) {
        System.out.println(command + ": command not found");
    }
}

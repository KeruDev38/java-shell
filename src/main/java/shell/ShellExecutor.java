package shell;

import shell.command.Execution;
import shell.command.CommandType;
import shell.command.ShellInput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
            resolveUnknown(input);
            return true;
        }
    }

    private void resolveUnknown(ShellInput input) {
        String fullPath = findExecutable(input.getCommand());

        if (fullPath != null) {
            executeProgram(input, fullPath);
        } else {
            commandNotFound(input.getCommand());
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
        }

        if (ctx.getEnv() != null && !ctx.getEnv().isEmpty()) {
            String fullPath = findExecutable(targetStr);
            if (fullPath != null) {
                System.out.println(targetStr + " is " + fullPath);
                return true;
            }
        }

        System.out.println(targetStr + ": not found");
        return true;
    }



    private boolean exit(ShellInput input) {
        return false;
    }

    private void commandNotFound(String command) {
        System.out.println(command + ": command not found");
    }

    private void executeProgram(ShellInput input, String path) {
        List<String> executable = new ArrayList<>();
        executable.add(path);
        String arguments = input.getArguments();
        if (arguments != null && !arguments.isEmpty()) {
            executable.addAll(
                    Arrays.asList(arguments.trim().split(" "))
            );
        }

        System.out.println("Program was passed " + executable.size() + " args (including program name).");
        System.out.println("Arg #0 (program name): " + input.getCommand());
        for (int i = 1; i < executable.size(); i++) {
            System.out.println("Arg #" + i + ": " + executable.get(i));
        }

        ProcessBuilder pb = new ProcessBuilder(executable);
        try {
            System.out.println(pb.start().pid());
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    private String findExecutable(String target) {
        for (String route : ctx.getEnv().split(File.pathSeparator)) {
            Path fullPath = Paths.get(route, target);

            if (Files.isExecutable(fullPath)) {
                return fullPath.toString();
            }
        }
        return null;
    }
}

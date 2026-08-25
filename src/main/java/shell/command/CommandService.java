package shell.command;

import shell.DirectoryResolver;
import shell.ShellContext;
import shell.reader.ShellInput;
import shell.exception.InvalidCommandException;

import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class CommandService {
    private final DirectoryResolver resolver;
    private final ShellContext ctx;
    private final Map<CommandType, Execution> commandsMap =
            new EnumMap<>(CommandType.class);

    public CommandService(
            ShellContext ctx,
            DirectoryResolver resolver
    ) {
        this.ctx = ctx;
        this.resolver = resolver;

        commandsMap.put(CommandType.ECHO, this::echo);
        commandsMap.put(CommandType.TYPE, this::type);
        commandsMap.put(CommandType.PWD, this::pwd);
        commandsMap.put(CommandType.CD, this::cd);
        commandsMap.put(CommandType.EXIT, this::exit);
    }

    private boolean echo(ShellInput input) {
        System.out.println(String.join(" ", input.getArguments()));
        return true;
    }

    private boolean type(ShellInput input) {
        if (!hasExactlyOneArgument(input)) {
            return true;
        }

        String target = input.getArguments().getFirst();

        if (isCommand(target)) {
            System.out.println(target + " is a shell builtin");
            return true;
        }

        Optional<Path> executable = resolver.findExecutable(target);

        if (executable.isPresent()) {
            System.out.println(target + " is " + executable.get());
            return true;
        }

        System.out.println(target + ": not found");
        return true;
    }

    private boolean pwd(ShellInput input) {
        System.out.println(ctx.getWorkingDir());
        return true;
    }

    public boolean cd(ShellInput input) {
        if (!hasExactlyOneArgument(input)) {
            return true;
        }

        String target = input.getArguments().getFirst();

        Optional<Path> directory = resolver.findDir(target);

        if (directory.isPresent()) {
            ctx.setWorkingDir(directory.get());
        } else {
            System.out.println("cd: " + target + ": No such file or directory");
        }
        return true;
    }

    private boolean exit(ShellInput input) { return false; }

    public boolean callCommand(ShellInput input) throws InvalidCommandException {
        CommandType command = CommandType.fromString(input.getCommand());
        if (!isCommand(command)) throw new InvalidCommandException(input + " is not a builtin command.");

        return commandsMap.get(command).execute(input);
    }

    public boolean isCommand(String input) {
        CommandType type = CommandType.fromString(input);
        return type != null && commandsMap.containsKey(type);
    }

    private boolean isCommand(CommandType type) {
        return type != null && commandsMap.containsKey(type);
    }

    private boolean hasExactlyOneArgument(ShellInput input) {
        if (input.getArguments().size() != 1) {
            System.out.println(
                    input.getCommand() + ": expected exactly one argument"
            );
            return false;
        }

        return true;
    }
}

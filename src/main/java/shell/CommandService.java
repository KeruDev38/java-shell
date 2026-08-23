package shell;

import shell.command.CommandType;
import shell.command.Execution;
import shell.command.ShellInput;
import shell.exception.InvalidCommandException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class CommandService {
    private final ExecutableResolver resolver;
    private final ShellContext ctx;
    private final Map<CommandType, Execution> commandsMap =
            new EnumMap<>(CommandType.class);

    public CommandService(
            ShellContext ctx,
            ExecutableResolver resolver
    ) {
        this.ctx = ctx;
        this.resolver = resolver;

        commandsMap.put(CommandType.ECHO, this::echo);
        commandsMap.put(CommandType.TYPE, this::type);
        commandsMap.put(CommandType.PWD, this::pwd);
        commandsMap.put(CommandType.EXIT, this::exit);
    }

    private boolean echo(ShellInput input) {
        System.out.println(input.getArguments());
        return true;
    }

    private boolean type(ShellInput input) {
        String target = input.getArguments().trim();

        if (isCommand(target)) {
            System.out.println(target + " is a shell builtin");
            return true;
        }

        Optional<String> executable = resolver.find(target);

        if (executable.isPresent()) {
            System.out.println(target + " is " + executable.get());
            return true;
        }

        System.out.println(target + ": not found");
        return true;
    }

    private boolean pwd(ShellInput input) {
        System.out.println(ctx.getCurrentDir());
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
}

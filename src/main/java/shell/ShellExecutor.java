package shell;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

public class ShellExecutor {
    private final Map<Command, Consumer<String>> commandsMap = new EnumMap<>(Command.class);
    private boolean isRunning = true;

    public ShellExecutor() {
        commandsMap.put(Command.ECHO, this::echo);
        commandsMap.put(Command.TYPE, this::type);
        commandsMap.put(Command.EXIT, this::exit);
    }

    public void execute(ShellCommand input) {
        Command command = Command.fromString(input.getCommand());

        if(command != null && commandsMap.containsKey(command)) {
            commandsMap.get(command);
        } else {
            commandNotFound(input.getCommand());
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void commandNotFound(String command) {
        System.out.println(command + ": command not found");
    }

    private void echo(String argument) {
        System.out.println(argument);
    }

    private void type(String argument) {
        // Si el argumento es un commando, lo explica, sino, llama a commandNotFound
    }

    private void exit(String argument) {
        this.isRunning = false;
    }
}

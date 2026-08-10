package shell.command;

@FunctionalInterface
public interface Execution {
    boolean execute(ShellInput input);
}

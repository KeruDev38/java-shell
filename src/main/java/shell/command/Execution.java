package shell.command;

import shell.reader.ShellInput;

@FunctionalInterface
public interface Execution {
    boolean execute(ShellInput input);
}

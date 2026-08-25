package shell;

import shell.command.CommandService;
import shell.reader.ShellInput;

import java.nio.file.Path;
import java.util.*;

public class ShellExecutor {
    private final CommandService commandService;
    private final DirectoryResolver dirResolver;
    private final ProcessExecutor processExecutor;

    public ShellExecutor(
            CommandService commandService,
            DirectoryResolver resolver,
            ProcessExecutor processExecutor
    ) {
        this.commandService = commandService;
        this.dirResolver = resolver;
        this.processExecutor = processExecutor;
    }

    public boolean execute(ShellInput input) {
        if (commandService.isCommand(input.getCommand())) {
            return commandService.callCommand(input);
        } else {
            resolveUnknown(input);
            return true;
        }
    }

    private void resolveUnknown(ShellInput input) {
        Optional<Path> fullPath = dirResolver.findExecutable(input.getCommand());

        if (fullPath.isPresent()) {
            processExecutor.executeProgram(input);
        } else {
            commandNotFound(input.getCommand());
        }
    }

    private void commandNotFound(String command) {
        System.out.println(command + ": command not found");
    }
}

package shell;

import shell.command.ShellInput;

import java.nio.file.Path;
import java.util.*;

public class ShellExecutor {
    private final CommandService commandService;
    private final DirectoryResolver execResolver;
    private final ProcessExecutor processExecutor;

    public ShellExecutor(
            CommandService commandService,
            DirectoryResolver resolver,
            ProcessExecutor processExecutor
    ) {
        this.commandService = commandService;
        this.execResolver = resolver;
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
        Optional<Path> fullPath = execResolver.findExecutable(input.getCommand());

        if (fullPath.isPresent()) {
            processExecutor.executeProgram(fullPath);
        } else {
            commandNotFound(input.getCommand());
        }
    }

    private void commandNotFound(String command) {
        System.out.println(command + ": command not found");
    }
}

package shell;

import shell.command.ShellInput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessExecutor {
    public void executeProgram(ShellInput input) {
        List<String> executable = new ArrayList<>();
        executable.add(input.getCommand());

        String arguments = input.getArguments();
        if (arguments != null && !arguments.isEmpty()) {
            executable.addAll(
                    Arrays.asList(arguments.trim().split("\\s+"))
            );
        }

        ProcessBuilder pb = new ProcessBuilder(executable);
        pb.inheritIO();

        try {
            pb.start().waitFor();
        } catch (IOException | InterruptedException e ) {
            Thread.currentThread().interrupt();
        }
    }
}

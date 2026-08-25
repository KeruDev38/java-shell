package shell;

import shell.reader.ShellInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessExecutor {
    public void executeProgram(ShellInput input) {
        List<String> executable = new ArrayList<>();

        executable.add(input.getCommand());
        executable.addAll(input.getArguments());

        try {
            new ProcessBuilder(executable)
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e ) {
            Thread.currentThread().interrupt();
        }
    }
}

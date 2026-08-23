package shell;

import java.nio.file.Path;

public class ShellContext {
    private final String path = System.getenv("PATH");
    private Path workingDir = Path.of("").toAbsolutePath();

    public String getPath() {
        return path;
    }

    public Path getWorkingDir() {
        return workingDir;
    }
}

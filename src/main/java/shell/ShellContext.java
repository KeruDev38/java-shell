package shell;

import java.nio.file.Path;
import java.util.Objects;

public class ShellContext {
    private final String path =
            Objects.requireNonNullElse(System.getenv("PATH"), "");

    private final Path home =
            Path.of(Objects.requireNonNullElse(
                    System.getProperty("user.home"),
                    "."
            ));

    private Path workingDir =
            Path.of("").toAbsolutePath();

    public String getPath() {
        return this.path;
    }

    public Path getWorkingDir() {
        return this.workingDir;
    }

    public Path getHome() {
        return this.home;
    }

    public void setWorkingDir(Path workingDir) {
        this.workingDir = workingDir;
    }
}

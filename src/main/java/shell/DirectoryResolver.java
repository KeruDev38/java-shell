package shell;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class DirectoryResolver {
    private final ShellContext ctx;

    public DirectoryResolver(ShellContext ctx) {
        this.ctx = ctx;
    }

    public Optional<String> findExecutable(String target) {
        for (String route : ctx.getPath().split(File.pathSeparator)) {
            Path fullPath = Paths.get(route, target);

            if (Files.isExecutable(fullPath)) {
                return Optional.of(fullPath.toString());
            }
        }
        return Optional.empty();
    }

    public Optional<Path> find(String target) {
        Path targetPath = Path.of(target);

        if (targetPath.isAbsolute()) {
            return Files.exists(targetPath) ?
                    Optional.of(targetPath) :
                    Optional.empty();
        }

        for (String route : ctx.getPath().split(File.pathSeparator)) {
            Path fullPath = Paths.get(route, target);

            if (Files.exists(fullPath)) {
                return Optional.of(fullPath);
            }
        }
        return Optional.empty();
    }
}

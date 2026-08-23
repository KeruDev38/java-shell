package shell;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class DirectoryResolver {
    private final ShellContext ctx;

    public DirectoryResolver(ShellContext ctx) {
        this.ctx = ctx;
    }

    public Optional<Path> findExecutable(String target) {
        Path targetPath = expandHome(target);

        // Absolute
        if(targetPath.isAbsolute()) {
            Path normalized = targetPath.normalize();

            return Files.isExecutable(normalized) ?
                    Optional.of(normalized) :
                    Optional.empty();
        }

        // Local
        Path currentWorkDir = ctx.getWorkingDir();
        Path resolvedLocal = currentWorkDir.resolve(targetPath).normalize();

        if (Files.isExecutable(resolvedLocal)) {
            return Optional.of(resolvedLocal);
        }

        // Simple name
        if (target.contains("/") || target.contains(File.separator))
            return Optional.empty();

        for (String route : ctx.getPath().split(File.pathSeparator)) {
            Path fullPath = Path.of(route).resolve(targetPath).normalize();

            if (Files.isExecutable(fullPath)) {
                return Optional.of(fullPath);
            }
        }
        return Optional.empty();
    }

    public Optional<Path> find(String target) {
        Path targetPath = expandHome(target);

        // Absolute
        if (targetPath.isAbsolute()) {
            Path normalized = targetPath.normalize();

            return Files.exists(normalized) ?
                    Optional.of(normalized) :
                    Optional.empty();
        }

        // Local
        Path currentWorkDir = ctx.getWorkingDir();
        Path resolvedLocal = currentWorkDir.resolve(targetPath).normalize();

        if (Files.exists(resolvedLocal)) {
            return Optional.of(resolvedLocal);
        }

        // Simple name
        if (target.contains("/") || target.contains(File.separator))
            return Optional.empty();

        for (String route : ctx.getPath().split(File.pathSeparator)) {
            Path fullPath = Path.of(route).resolve(targetPath).normalize();

            if (Files.exists(fullPath)) {
                return Optional.of(fullPath);
            }
        }

        return Optional.empty();
    }

    private Path expandHome(String target) {
        if (target.equals("~")) {
            return ctx.getHome();
        }
        if (target.startsWith("~/" ) || target.startsWith("~\\")) {
            return ctx.getHome().resolve(target.substring(2));
        }
        return Path.of(target);
    }
}

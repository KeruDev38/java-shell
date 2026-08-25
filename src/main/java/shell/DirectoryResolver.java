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

            return Files.isRegularFile(normalized)
                    ? Optional.of(normalized)
                    : Optional.empty();
        }

        // Local
        if (target.contains("/") || target.contains(File.separator)) {
            Path resolved = ctx.getWorkingDir()
                    .resolve(targetPath)
                    .normalize();

            return Files.isRegularFile(resolved)
                    ? Optional.of(resolved)
                    : Optional.empty();
        }

        // Simple name
        for (String route : ctx.getPath().split(File.pathSeparator)) {
            Path fullPath = Path.of(route).resolve(targetPath).normalize();

            if (Files.isExecutable(fullPath)) {
                return Optional.of(fullPath);
            }
        }
        return Optional.empty();
    }

    public Optional<Path> findDir(String target) {
        Path targetPath = expandHome(target);

        Path resolved = targetPath.isAbsolute()
                ? targetPath.normalize()
                : ctx.getWorkingDir()
                    .resolve(targetPath)
                    .normalize();

        return Files.isDirectory(resolved)
                ? Optional.of(resolved)
                : Optional.empty();
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

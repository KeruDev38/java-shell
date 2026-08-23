package shell;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ExecutableResolver {
    private final ShellContext ctx;

    public ExecutableResolver(ShellContext ctx) {
        this.ctx = ctx;
    }

    public Optional<String> find(String target) {
        for (String route : ctx.getPath().split(File.pathSeparator)) {
            Path fullPath = Paths.get(route, target);

            if (Files.isExecutable(fullPath)) {
                return Optional.of(fullPath.toString());
            }
        }
        return Optional.empty();
    }
}

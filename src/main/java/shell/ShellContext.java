package shell;

public class ShellContext {
    private final String path = System.getenv("PATH");
    private String currentDir = path;

    public String getPath() {
        return path;
    }

    public String getCurrentDir() {
        return currentDir;
    }

    public String forwardDir(String dir) {
        currentDir = currentDir + dir;
        return currentDir;
    }
}

package shell;

public class ShellContext {
    private final String env = System.getenv("PATH");

    public String getEnv() {
        return env;
    }
}

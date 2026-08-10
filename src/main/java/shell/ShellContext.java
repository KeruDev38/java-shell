package shell;

public class ShellContext {
    private boolean isRunning = true;

    public void stop() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}

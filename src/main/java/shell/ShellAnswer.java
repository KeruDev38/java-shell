package shell;

public class ShellAnswer {
    public void commandNotFound(String command) {
        System.out.println(command + ": command not found");
    }

    public void echo(String argument) {
        System.out.println(argument);
    }
}

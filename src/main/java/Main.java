import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        run(sc);
    }

    private static void run(Scanner sc) {
        System.out.print("$ ");
        String input = sc.next();
        System.out.println(input + ": command not found");
    }
}

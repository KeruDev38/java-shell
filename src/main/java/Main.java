import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        // TODO hashmap with lambdas
        run(sc);
    }

    private static void run(Scanner sc) {
        while (true) {
            System.out.print("$ ");
            String input = sc.nextLine();
            if (input.equals("exit")) break;
            System.out.println(input + ": command not found");
        }

    }
}

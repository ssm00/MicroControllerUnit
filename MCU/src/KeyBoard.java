import java.util.Scanner;

public class KeyBoard {
    private Scanner scanner;
    public KeyBoard() {
        scanner = new Scanner(System.in);
    }
    public int input() {
        int i = scanner.nextInt();
        return i;
    }
    public void output(int value) {
        System.out.println(value);
    }
}

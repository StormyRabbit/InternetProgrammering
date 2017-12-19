import java.util.Scanner;

public class EntryPoint {
    private Scanner scan;
    public EntryPoint() throws Exception {
        scan = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = scan.nextLine();
        System.out.println("Enter E-mail address");
        String email = scan.nextLine();
        new XMLChatClient(name, email);
    }

    public static void main(String[] args) {
        try {
            new EntryPoint();
        }catch (Exception e) {

        }

    }
}

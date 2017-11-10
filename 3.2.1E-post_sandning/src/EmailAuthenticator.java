import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Scanner;

public class EmailAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Login name:");
        String loginName = scanner.nextLine();
        System.out.println("Password:");
        String password = scanner.nextLine();
        return new PasswordAuthentication(loginName, password);
    }
}

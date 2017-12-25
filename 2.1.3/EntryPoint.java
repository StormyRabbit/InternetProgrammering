import java.util.Scanner;

public class EntryPoint {
    private Scanner scan;
    public EntryPoint() throws Exception {
        new XMLChatClient();
    }

    public static void main(String[] args) {
        try {
            new EntryPoint();
        }catch (Exception e) {

        }

    }
}

import java.util.Scanner;

public class Cli {
    private MailClient mc;
    private Scanner scanner;
    private String[] commands = new String[] {
        "New mail", "Config STMP", "Send queued msgs"
    };
    public Cli() {
        mc = new MailClient();
        scanner = new Scanner(System.in);
        printMainMenu();
        while(true)
            readUserInput();
    }

    private void readUserInput() {

        int cmd = scanner.nextInt();
        handleInput(cmd);
    }

    private void handleInput(int cmd) {
            switch(cmd){
                case 0:
                    newMailOption();
                    break;
                case 1:
                    configSTMPOption();
                    break;
                case 2:
                    sendQueuedMsgsOption();
                    break;
                default:
                    break;
            }
    }

    private void newMailOption() {
        scanner.reset();
        EmailMsg msg = new EmailMsg();
        System.out.println("From: ");
        msg.setFrom(scanner.nextLine());
        System.out.println("To: ");
        msg.setTo(scanner.nextLine());
        System.out.println("Subject: ");
        msg.setSubject(scanner.nextLine());
        System.out.println("Message: ");
        StringBuilder msgbod = new StringBuilder();
        String input;
        while(!(input = scanner.nextLine()).equals("[endMsg]"))
            msgbod.append(input);

        msg.setMsgBody(msgbod.toString());
        readUserInput();
    }

    private void configSTMPOption() {
        System.out.println("set mail server:");
        mc.setMailServer(scanner.nextLine());
    }

    private void sendQueuedMsgsOption() {
        mc.sendAllQueuedMsgs();
    }

    private void printMainMenu() {
        for(int i = 0; i < commands.length; i++)
            System.out.println(i + ": " + commands[i]);
    }
}

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket chatSocket;
    private PrintWriter msgPW;
    private String ip = " atlas.dsv.su.se";
    private int port = 9494;
    private ChatInbox ci;
    private User usr;

    public ChatClient(String[] args) throws IOException {
        setupCustomIP(args);
        setupSocket();
        setupReader();
        ci = new ChatInbox(chatSocket);
        userInputLoop();
    }

    private void setupSocket() {
        try {
            chatSocket = new Socket(ip, port);
        }catch(IOException ioe){
            System.out.println("connection refused!");
        }
    }

    private void setupReader() {
        try {
            msgPW = new PrintWriter(new OutputStreamWriter(chatSocket.getOutputStream(), "ISO-8859-1"), true);
        }catch(IOException ioe) {
            System.out.println("Reader setup error");
        }
    }

    private void setupCustomIP(String[] args) {
        if(args.length > 0)
            ip = args[0];
        if(args.length > 1)
            port = Integer.valueOf(args[1]);
    }

    private void userInputLoop() {
        Scanner userInput = new Scanner(System.in);
        if(usr == null) {
            usr = new User();
            System.out.println("Enter name:");
            usr.setName(userInput.nextLine());
            System.out.println("Enter email:");
            usr.setEmail(userInput.nextLine());
            System.out.println("Enter homepage:");
            usr.setHomepage(userInput.nextLine());
            System.out.println("User created ... ");
        }

        while(true){
            msgPW.println(userInput.nextLine());
        }
    }
}
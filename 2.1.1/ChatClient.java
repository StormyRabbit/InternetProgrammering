import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket chatSocket;
    private PrintWriter msgPW;
    private String ip = "127.0.0.1";
    private int port = 2000;
    private ChatInbox ci;

    /**
     *  Default and only constructor, takes 
     * input from args for configuration
     */
    public ChatClient(String[] args) {
        setupCustomIP(args);
        setupSocket();
        setupReader();
        ci = new ChatInbox(chatSocket);
        userInputLoop();
    }

    private void setupSocket() {
        // creates the socket object using either default ip/port or user specified data.
        try {
            chatSocket = new Socket(ip, port);
        }catch(IOException ioe){
            System.out.println("connection refused!");
        }
        System.out.format("Connected to %s on port: %d \n", ip, port);
    }

    private void setupReader() {
        // Creates the stream object and catches any exceptions.
        try {
            msgPW = new PrintWriter(new OutputStreamWriter(chatSocket.getOutputStream(), "ISO-8859-1"), true);
        }catch(IOException ioe) {
            System.out.println("Reader setup error");
        }
    }

    private void setupCustomIP(String[] args) {
        // used for setting custom ip and port inputed when the program launches.
        if(args.length > 0)
            ip = args[0];
        if(args.length > 1)
            port = Integer.valueOf(args[1]);
    }

    private void userInputLoop() {
        // starts and maintains the loop reading from stdIn.
        Scanner userInput = new Scanner(System.in);
        while(true){
            msgPW.println(userInput.nextLine());
        }

    }


    public static void main(String[] args) {
        // main method responsible for creating the CC object.
        new ChatClient(args);

    }

}
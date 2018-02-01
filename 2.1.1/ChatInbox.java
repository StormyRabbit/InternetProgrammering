import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatInbox extends Thread {
    private Socket chatSocket;
    private BufferedReader chatReader;

    /**
     * default constructor, takes a socket object as param.
     */
    public ChatInbox(Socket socket) {
        this.chatSocket = socket;
        setupReader();

        this.start();
    }

    private void setupReader() {
        // creates and binds the input stream and catches any exceptions.
        try {
            chatReader = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
        }catch(IOException ioe) {
            System.out.println("Chat reader error!");
        }
    }

    @Override
    public void run() {
        // mainloop for the thread, listens for incoming data on the socket and sends to stdout.
        while(true) {
            try{
                String s = chatReader.readLine();
                System.out.println(s);
            }catch(IOException ioe) {}

        }
    }
}

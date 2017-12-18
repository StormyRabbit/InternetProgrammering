import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatInbox extends Thread {
    private Socket chatSocket;
    private BufferedReader chatReader;

    public ChatInbox(Socket socket) {
        this.chatSocket = socket;
        setupReader();

        this.start();
    }

    private void setupReader() {
        try {
            chatReader = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
        }catch(IOException ioe) {
            System.out.println("Chat reader error!");
        }
    }

    @Override
    public void run() {
        while(true) {
            try{
                String s = chatReader.readLine();
                System.out.println(s);
            }catch(IOException ioe) {}

        }
    }
}

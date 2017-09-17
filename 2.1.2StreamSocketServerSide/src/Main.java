import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private ServerSocket serSocket;
    private int port = 2000;
    private ConnectedClient ch;
    private ChatManager cm;

    public Main(String[] args) {
        if(args.length > 0)
            port = Integer.valueOf(args[0]);

        setupSocket();
        cm = new ChatManager();
        listenForRequests();
    }


    private void listenForRequests() {
        while(true)
            handleRequests();
    }

    private void handleRequests() {
        try {
            Socket clientSocket = serSocket.accept();
            cm.addClient(new ConnectedClient(clientSocket, cm));
        }catch(IOException ioe ) {
            System.out.println("HandleRequest IOException");
        }
    }

    private void setupSocket() {
        try {
            serSocket = new ServerSocket(port);
        }catch(IOException ioe) {
            System.out.println("SerSocket IOException");
        }
    }


    public static void main(String[] args) {
        new Main(args);
    }
}

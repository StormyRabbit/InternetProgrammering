import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private ServerSocket serSocket;
    private int port = 2000;
    private ChatManager cm;
    private JFrame window;

    public Main(String[] args) {
        super();
        if(args.length > 0)
            port = Integer.valueOf(args[0]);

        window = new JFrame();
        JLabel jl = new JLabel("OBS! detta fönster används enbart för titel baren, resterande information skrivs ut i terminalen");
        window.add(jl);
        window.setVisible(true);
        setupSocket();
        cm = new ChatManager(this);
        listenForRequests();

    }

    public void updateTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append("IP: ");
        sb.append(serSocket.getInetAddress().getHostName());
        sb.append("PORT: ");
        sb.append(port);
        sb.append("CONNECTED USERS: ");
        sb.append(cm.getNumConUsers());
        window.setTitle(sb.toString());
    }

    private void listenForRequests() {
        while(true)
            handleRequests();
    }

    private void handleRequests() {
        try {
            Socket clientSocket = serSocket.accept();
            cm.addClient(new ConnectedClient(clientSocket, cm));
            updateTitle();
        }catch(IOException ioe ) {
            System.out.println("HandleRequest IOException");
        }
    }

    private void setupSocket() {
        try {
            serSocket = new ServerSocket(port);
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Main(args);
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private ChatManager cm;
    private boolean alive;

    public ConnectedClient(Socket s, ChatManager cm) {
        this.clientSocket = s;
        this.cm = cm;
        setupReaders();
        this.alive = true;
        this.start();
    }

    private void setupReaders() {
        setupInReader();
        setupOutReader();
    }

    private void setupInReader() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch(IOException ioe) {
            System.out.println("InReader IOException");
        }
    }

    private void setupOutReader() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        }catch(IOException ioe) {
            System.out.println("outWriter IOException");
        }
    }

    @Override
    public void run() {
        while(alive)
            listenForInput();
    }

    private void listenForInput() {
        String msg;
        try {
            while( (msg = in.readLine()) != null )
                cm.broadCastMsg(msg);

            out.close();
            in.close();
            clientSocket.close();
            this.alive = false;
            cm.disconnectClient(this);
        }catch(IOException ioe ) {}

    }

    public void sendMessage(String s) {
        out.println(s);
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}

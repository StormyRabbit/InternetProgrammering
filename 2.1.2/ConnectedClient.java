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
        // main constructor, binds the parameter objects and starts the thread loop.
        this.clientSocket = s;
        this.cm = cm;
        setupReaders();
        this.alive = true;
        this.start();
    }

    private void setupReaders() {
        // calls the subprocesses that has the responsibility to create and bind the stream objects.
        setupInReader();
        setupOutReader();
    }

    private void setupInReader() {
        // creates and binds the input stream and catches any exception.
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch(IOException ioe) {
            System.out.println("InReader IOException");
        }
    }

    private void setupOutReader() {
        // creates and binds the output stream and catches any exception.
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        }catch(IOException ioe) {
            System.out.println("outWriter IOException");
        }
    }

    @Override
    public void run() {
        // Threads main loop.
        while(alive)
            listenForInput();
    }

    private void listenForInput() {
        // listens for input from a client and sends in to the ChatManager.
        // disconnects the client and cleans up the sockets when client sends null.
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
        // sends the parameter to the client.
        out.println(s);
    }

    public Socket getClientSocket() {
        // returns the socket object of the connected client.
        return clientSocket;
    }
}

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RestfulView extends Thread {
    private boolean isRunning;
    private ServerSocket socket;
    private Socket clientSocket;
    public RestfulView() {
        setupSocket();
        isRunning = true;
    }

    private void setupSocket() {
        try {
            socket = new ServerSocket();
        }catch (IOException ioe) {

        }
    }

    @Override
    public void run() {
        while(isRunning)
            listenForRequests();

    }

    private void listenForRequests() {
        try(Socket connection = socket.accept()) {

        }catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}

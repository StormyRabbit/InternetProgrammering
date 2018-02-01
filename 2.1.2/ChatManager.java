import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChatManager extends Thread {
    private Queue<String> msgQueue;
    private boolean alive;
    private List<ConnectedClient> conList;
    private Main main;

    public ChatManager(Main main) {
        // main construktor, creates the datastructures and then starts the thread loop.
        msgQueue = new LinkedList<>();
        alive = true;
        conList = new LinkedList<>();
        this.main = main;
        this.start();
    }

    @Override
    public void run() {
        // main loop for the thread.
        while(alive)
            handleQueue();
    }

    public int getNumConUsers() {
        // returns the number of connected users.
        return conList.size();
    }

    public void addClient(ConnectedClient cc) {
        /**
         * takes a ConnectedClient object as parameter and adds it to the collection
         * of connected users. Then broadcasts a msg to all the connected users.
         */
        StringBuilder sb = new StringBuilder();
        sb.append("New client connected: ");
        sb.append(cc.getClientSocket().getInetAddress().getHostName());
        broadCastMsg(sb.toString());
        System.out.println(sb.toString());
        conList.add(cc);
    }

    private void handleQueue() {
        // manages the msgQueue and calls the subprocces to transmitt any queued msgs.
        while(!msgQueue.isEmpty())
            broadCastMsg(msgQueue.poll());
    }

    public synchronized void disconnectClient(ConnectedClient cc) {
        // manages the disconnect process of a client
        // removes from the client list and transmitts a notice to the remaining clients.
            StringBuilder sb = new StringBuilder();
            sb.append("Client disconnected: ");
            sb.append(cc.getClientSocket().getInetAddress().getHostName());
            broadCastMsg(sb.toString());
            conList.remove(cc);
            main.updateTitle();
    }

    public synchronized void broadCastMsg(String s) {
        // transmits the param to all connected clients.
        for(ConnectedClient cc : conList) {
            cc.sendMessage(s);
        }
    }
}

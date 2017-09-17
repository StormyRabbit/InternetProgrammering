import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChatManager extends Thread {
    private Queue<String> msgQueue;
    private boolean alive;
    private List<ConnectedClient> conList;

    public ChatManager() {
        msgQueue = new LinkedList<>();
        alive = true;
        conList = new LinkedList<>();
        this.start();
    }

    @Override
    public void run() {
        while(alive)
            handleQueue();
    }

    public void addClient(ConnectedClient cc) {
        StringBuilder sb = new StringBuilder();
        sb.append("New client connected: ");
        sb.append(cc.getClientSocket().getInetAddress().getHostName());
        broadCastMsg(sb.toString());
        System.out.println(sb.toString());
        conList.add(cc);
    }

    private void handleQueue() {
        while(!msgQueue.isEmpty())
            broadCastMsg(msgQueue.poll());
    }

    public void disconnectClient(ConnectedClient cc) {
        StringBuilder sb = new StringBuilder();
        sb.append("Client disconnected: ");
        sb.append(cc.getClientSocket().getInetAddress().getHostName());
        broadCastMsg(sb.toString());
        conList.remove(cc);
    }

    public void broadCastMsg(String s) {
        System.out.println(s);

        for(ConnectedClient cc : conList) {
            cc.sendMessage(s);
        }
    }
}

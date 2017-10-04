import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class UDPSender extends Thread {
    private DatagramSocket dgs;
    private InetAddress targetHost;
    private int port;
    private boolean alive;
    private Queue<Point> udpQueue;

    public UDPSender(String[] args, DatagramSocket ds) {
        try {
            targetHost = InetAddress.getByName("localhost");
        }catch(UnknownHostException uhe) { }

        if(args.length > 2){
            try {
                targetHost = InetAddress.getByName(args[1]);
            }catch(UnknownHostException uhe) { }
            port = Integer.valueOf(args[2]);
        }

        udpQueue = new LinkedList<>();

        alive = true;
        this.start();
    }

    public void addPointToQueue(Point p ) {
        udpQueue.add(p);
    }

    private void sendPoint(Point p) {
        System.out.println("test");
        byte[] byteArr = convertPointToByteArr(p);
        DatagramPacket packet = new DatagramPacket(byteArr, byteArr.length, targetHost, port);
        try {
            dgs.send(packet);
        }catch(IOException ioe) {
        }
    }

    private byte[] convertPointToByteArr(Point p) {
        StringBuilder sb = new StringBuilder();
        sb.append(p.getX());
        sb.append(",");
        sb.append(p.getY());
        return sb.toString().getBytes();

    }

    @Override
    public void run() {
        System.out.println("starting run");
        while(alive)
            manageQueue();
    }

    private void manageQueue() {
        while(!udpQueue.isEmpty()){
            System.out.println("point retrived");
            sendPoint(udpQueue.poll());
        }

    }
}

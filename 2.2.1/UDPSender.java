import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

public class UDPSender {
    private DatagramSocket dgs;
    private InetAddress targetHost;
    private int port;
    public UDPSender(String[] args) {
        /**
         * main constructor, creates and binds the needed objects using the input arguments.
         */
        try {
            dgs = new DatagramSocket();                    
            targetHost = InetAddress.getByName(args[1]);
        }catch(Exception e) { }
        port = Integer.valueOf(args[2]);
    }


    public void sendPoint(Point p) {
        /**
         * sends a point to the connected host.
         */
        byte[] byteArr = convertPointToByteArr(p);        
        try {
            DatagramPacket packet = new DatagramPacket(byteArr, byteArr.length, targetHost, port);            
            dgs.send(packet);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] convertPointToByteArr(Point p) {
        // prepares a point for transmission by converting it to a string.
        StringBuilder sb = new StringBuilder();
        sb.append(p.x);
        sb.append(",");
        sb.append(p.y);
        return sb.toString().getBytes();
    }
}

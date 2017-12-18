import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceiver extends Thread implements Observable {

    private boolean alive;
    private DatagramSocket socket;
    private guiObserver guiObserver;

    public UDPReceiver(String[] args, guiObserver guiObserver) throws Exception{
        this.guiObserver = guiObserver;
        socket = new DatagramSocket(Integer.valueOf(args[0]));
        alive = true;
        this.start();
    }

    public void updateObservers(Point p) {
        guiObserver.update(p);
    }

    @Override
    public void run() {
        while(alive)
            listenForCommunication();
    }

    private void listenForCommunication() {
        try {
            DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
            socket.receive(dp);
            String cdp = convertDatagramPacket(dp);
            Point newPoint = stringToPointConverter(cdp);
            updateObservers(newPoint);
        }catch( Exception e) {}
    }

    private Point stringToPointConverter(String pointString) {        
        String[] stringArr = pointString.split(",");
        int x = Integer.parseInt(stringArr[0]);
        int y = Integer.parseInt(stringArr[1]);        
        return new Point(x, y);
    }


    private String convertDatagramPacket(DatagramPacket dp) {
        String s = new String(dp.getData(), dp.getOffset(), dp.getLength());
        return s;
    }
}

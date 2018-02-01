import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReceiver extends Thread implements Observable {

    private boolean alive;
    private DatagramSocket socket;
    private guiObserver guiObserver;

    public UDPReceiver(String[] args, guiObserver guiObserver) throws Exception{
        /**
         *  main constructor for the class, responsible for creating and binding the nedded objects.
         * 
         */
        this.guiObserver = guiObserver;
        socket = new DatagramSocket(Integer.valueOf(args[0]));
        alive = true;
        this.start();
    }

    public void updateObservers(Point p) {
        // sends the incoming point to the GUI.
        guiObserver.update(p);
    }

    @Override
    public void run() {
        // threads main loop
        while(alive)
            listenForCommunication();
    }

    private void listenForCommunication() {
        // process for listening for incoming UDP packets and converting the datagram to a PointObject and then notifies the observers.
        try {
            DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
            socket.receive(dp);
            String cdp = convertDatagramPacket(dp);
            Point newPoint = stringToPointConverter(cdp);
            updateObservers(newPoint);
        }catch( Exception e) {}
    }

    private Point stringToPointConverter(String pointString) {
        // converts a string to a Point object.        
        String[] stringArr = pointString.split(",");
        int x = Integer.parseInt(stringArr[0]);
        int y = Integer.parseInt(stringArr[1]);        
        return new Point(x, y);
    }


    private String convertDatagramPacket(DatagramPacket dp) {
        // converts a DatagramPacket to a string.
        String s = new String(dp.getData(), dp.getOffset(), dp.getLength());
        return s;
    }
}

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class UDPReceiver extends Thread implements Observable {

    private boolean alive;
    private DatagramSocket socket;
    private guiObserver guiObserver;

    public UDPReceiver(DatagramSocket socket, guiObserver guiObserver) {

        this.socket = socket;
        this.guiObserver = guiObserver;
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
        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        try {
            socket.receive(dp);
        }catch(IOException ioe) { }
        updateObservers(stringToPointConverter(convertDatagramPacket(dp)));
    }

    private Point stringToPointConverter(String pointString) {
        String[] stringArr = pointString.split(",");
        int x = Integer.valueOf(stringArr[0]);
        int y = Integer.valueOf(stringArr[1]);
        return new Point(x, y);
    }


    private String convertDatagramPacket(DatagramPacket dp) {
        return new String(dp.getData(), 0, dp.getLength());
    }
}

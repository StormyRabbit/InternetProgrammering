import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ImageReceiver extends Thread{
    private Socket s;
    private boolean running;
    private ObjectInputStream in;

    public ImageReceiver(Socket s) {
        // main constructor for the class responsibile for handeling incoming files.
        // takes a socket to listen on as parameter and then starts the loop.
        this.s = s;
        running = true;
        this.start();
    }


    @Override
    public void run() {
        /**
         * Threads main loop, listen for incoming Objects from the input stream
         * notifies the client of the incoming data and then builds the file.
         */
        try {
            in = new ObjectInputStream(s.getInputStream());
        }catch(Exception e) {
            e.printStackTrace();
        }
        while(running) {
            try {
                Storage storage = (Storage)in.readObject();
                System.out.printf("recieved object %s...\n", storage.getId());
                try {
                    FileOutputStream fos = new FileOutputStream(new StringBuilder().append("in_").append(storage.getId()).toString());
                    fos.write(storage.getData());
                }catch (Exception e) {

                }
            }catch (Exception e) {
            }
        }
    }
}

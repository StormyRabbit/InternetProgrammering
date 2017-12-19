import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ImageReceiver extends Thread{
    private Socket s;
    private boolean running;
    private ObjectInputStream in;

    public ImageReceiver(Socket s) {
        this.s = s;
        running = true;
        this.start();
    }


    @Override
    public void run() {
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

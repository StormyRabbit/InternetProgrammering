import java.net.Socket;
import java.util.Scanner;

public class ImageClient {

    private ImageSender is;
    private ImageReceiver ir;
    private Scanner scan;
    private Socket s;
    public ImageClient() throws Exception {
        s = new Socket("127.0.1", 4848);
        is = new ImageSender(s);
        ir = new ImageReceiver(s);
        startCLI();
    }

    private void startCLI() throws Exception {
        scan = new Scanner(System.in);
        while(true) {
            System.out.println("Enter filename of file to send:");
            String file = scan.nextLine();
            is.addFileToQueue(file);
            is.manageOutQueue();
        }
    }


}

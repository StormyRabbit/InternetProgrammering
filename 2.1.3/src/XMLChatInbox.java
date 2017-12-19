import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class XMLChatInbox extends Thread {
    private Socket s;
    private BufferedReader chatReader;
    public XMLChatInbox(Socket s) throws Exception {
        this.s = s;
        chatReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        while(true) {
            try {
                String s = chatReader.readLine();
                System.out.println(s);
            }catch(Exception e) {

            }
        }
    }
}

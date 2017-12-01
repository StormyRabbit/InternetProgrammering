import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;
public class KeyHandler {
    private KeyPair kp;
    private KeyPairGenerator kpg;

    private ObjectOutputStream oos;

    public KeyHandler(String[] args) throws Exception {
        setupKPG();
        writeToKey(kp.getPrivate(), args[0]);
        writeToKey(kp.getPublic(), args[1]);
    }

    private void setupKPG() throws Exception {
        kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        kp = kpg.generateKeyPair();
    }
    private void writeToKey(Key key, String s) throws Exception {
        oos = new ObjectOutputStream(new FileOutputStream(s));
        oos.writeObject(key);
    }

    public static void main(String[] args) {
        try {
            new KeyHandler(args);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}

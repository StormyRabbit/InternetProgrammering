import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;
public class KeyHandler {
    private KeyPair kp;
    private KeyPairGenerator kpg;

    private ObjectOutputStream oos;

    public KeyHandler(String[] args) throws Exception {
        /**
         * constructor, calls all the needed subroutines with the needed arguments.
         */
        setupKPG();
        writeToKey(kp.getPrivate(), args[0]);
        writeToKey(kp.getPublic(), args[1]);
    }

    private void setupKPG() throws Exception {
        // setups and intialize the keypair generator object.
        kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        kp = kpg.generateKeyPair();
    }
    private void writeToKey(Key key, String s) throws Exception {
        // creates a binary file of the provided Key object.
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

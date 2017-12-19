import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class KeyHandler {

    private KeyGenerator keygen;
    private SecretKey key;
    private ObjectOutputStream oos;
    private FileOutputStream fos;

    public KeyHandler(String[] args) throws NoSuchAlgorithmException, IOException {
        keygen = KeyGenerator.getInstance("Blowfish");
        keygen.init(448);
        key = keygen.generateKey();
        fos = new FileOutputStream(args[0]);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(key);
    }

    public static void main(String[] args) {
        try {
            new KeyHandler(args);
        }catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}

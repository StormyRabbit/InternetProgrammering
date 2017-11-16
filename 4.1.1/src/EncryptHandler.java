import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;

public class EncryptHandler {

    private FileInputStream fis;
    private ObjectInputStream ois;
    private SecretKey key;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private Cipher cipher;

    public EncryptHandler(String[] args) throws Exception {
        loadFile(args[0]);
        loadSecretKey(args[1]);
        createEncryptedFile(args[2]);
    }

    private void loadFile(String fileName) throws Exception {
        fis = new FileInputStream(fileName);
        bis = new BufferedInputStream(fis);
    }

    private void loadSecretKey(String secretKey) throws IOException, ClassNotFoundException {
        fis = new FileInputStream(secretKey);
        ois = new ObjectInputStream(fis);
        key = (SecretKey)ois.readObject();
    }

    private void createEncryptedFile(String outputFileName) {

    }

    public static void main(String[] args) {
        try {
            new EncryptHandler(args);
        }catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}

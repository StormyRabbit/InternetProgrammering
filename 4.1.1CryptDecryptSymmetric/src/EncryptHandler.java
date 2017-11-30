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
    private static final String ALGORITHM = "Blowfish/ECB/PKCS5Padding";

    public EncryptHandler(String[] args) throws Exception {
        loadFile(args[0]);
        loadSecretKey(args[1]);
        setupCipher();
        createEncryptedFile(args[2]);
    }

    private void setupCipher() throws Exception {
        cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
    }

    private void loadFile(String fileName) throws Exception {
        fis = new FileInputStream(fileName);
        bis = new BufferedInputStream(fis);
    }

    private void loadSecretKey(String secretKey) throws Exception {
        ois = new ObjectInputStream(new FileInputStream(secretKey));
        key = (SecretKey)ois.readObject();
    }

    private void createEncryptedFile(String outputFileName) throws Exception {
        setupOutput(outputFileName);
        byte[] inByte = new byte[1024];
        while(bis.available() != 0) {
            int length = bis.read(inByte);
            bos.write(cipher.update(inByte, 0 ,length));
        }
        bos.write(cipher.doFinal());
        bos.close();
        bis.close();
    }

    private void setupOutput(String outputFileName) throws Exception {
        bos = new BufferedOutputStream(new FileOutputStream(outputFileName));
    }

    public static void main(String[] args) {
        try {
            new EncryptHandler(args);
        }catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}

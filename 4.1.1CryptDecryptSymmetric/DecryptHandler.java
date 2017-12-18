import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;

public class DecryptHandler {
    private static final String ALGORITHM = "Blowfish/ECB/PKCS5Padding";
    private ObjectInputStream ois;
    private SecretKey key;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private Cipher cipher;

    public DecryptHandler(String[] args) throws Exception {
        loadEncryptedData(args[0]);
        loadKey(args[1]);
        setupCipher();
        writeDecryptedData(args[2]);
        
    }

    private void setupCipher() throws Exception {
        cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    private void writeDecryptedData(String arg) throws Exception {
        bos = new BufferedOutputStream(new FileOutputStream(arg));
        byte[] inByte = new byte[1024];
        while(bis.available() != 0) {
            int length = bis.read(inByte);
            bos.write(cipher.update(inByte, 0, length));
        }
        bos.write(cipher.doFinal());
        bos.close();
        bis.close();
    }

    private void loadEncryptedData(String arg) throws Exception {
        bis = new BufferedInputStream(new FileInputStream(arg));
    }

    private void loadKey(String arg) throws Exception {
        ois = new ObjectInputStream(new FileInputStream(arg));
        key = (SecretKey)ois.readObject();
    }

    public static void main(String[] args) {
        try {
            new DecryptHandler(args);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

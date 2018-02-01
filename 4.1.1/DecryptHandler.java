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
        /**
         * constructor, takes launch parameters and starts decryption process.
         * parameters: 
         * 0 = encryptedData
         * 1 = secretKey
         * 2 = decryptedData
         */
        loadEncryptedData(args[0]);
        loadKey(args[1]);
        setupCipher();
        writeDecryptedData(args[2]);
        
    }

    private void setupCipher() throws Exception {
        // setups the Cipher object
        cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
    }

    private void writeDecryptedData(String arg) throws Exception {
        /*
            decrypts the data and writes it to the file represented by the param string.
        */
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
        // load a instream with the file from the String param.
        bis = new BufferedInputStream(new FileInputStream(arg));
    }

    private void loadKey(String arg) throws Exception {
        // loads a key file and creates a secretKey object.
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

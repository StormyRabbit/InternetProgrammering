import java.io.*;
import java.security.*;

public class SignHandler {


    private PrivateKey pk;
    private Signature sig;

    private BufferedInputStream bis;
    private BufferedOutputStream bos;

    public SignHandler(String[] args) throws Exception {
        loadPK(args[1]);
        loadSignature();
        startSignatureProcess(args[0], args[2]);
    }

    private void startSignatureProcess(String inFile, String outFile) throws Exception {
        bis = new BufferedInputStream(new FileInputStream(inFile));
        byte[] inByte = new byte[1024];

        while(bis.available() != 0) {
            int length = bis.read(inByte);
            sig.update(inByte, 0, length);
        }
        bis.close();
        byte[] sigByte = sig.sign();
        bos = new BufferedOutputStream(new FileOutputStream(outFile));
        bos.write(sigByte);
        bos.close();

    }

    private void loadSignature() throws Exception {
        sig = Signature.getInstance("SHA1withDSA");
        sig.initSign(pk);
    }

    private void loadPK(String keyFile) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(keyFile));
        pk = (PrivateKey)ois.readObject();
    }

    public static void main(String[] args) {
        try {
            new SignHandler(args);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

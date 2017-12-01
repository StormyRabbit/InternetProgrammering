import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.security.Signature;

public class VerifyHandler {

    private PublicKey pk;
    private Signature sig;
    private byte[] sigByteArr;
    private BufferedInputStream bis;
    public VerifyHandler(String[] args) throws Exception {
        loadData(args[0]);
        loadPublicKey(args[1]);
        loadSignature(args[2]);
        verifyData();
    }

    private void verifyData() throws Exception{
        byte[] inByte = new byte[1024];
        while(bis.available() > 0) {
            int length = bis.read(inByte);
            sig.update(inByte, 0, length);
        }
        bis.close();
        System.out.println(sig.verify(sigByteArr) ? "sig verified" : "sig not verified");
    }

    private void loadData(String arg) throws Exception {
        bis = new BufferedInputStream(new FileInputStream(arg));
    }

    private void loadPublicKey(String pkFile) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pkFile));
        pk = (PublicKey) ois.readObject();
    }

    public static void main(String[] args) {
        try {
            new VerifyHandler(args);
        }catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void loadSignature(String sigFile) throws Exception {
        sig = Signature.getInstance("SHA1withDSA");
        sig.initVerify(pk);
        FileInputStream fis = new FileInputStream(sigFile);
        sigByteArr = new byte[fis.available()];
        fis.read(sigByteArr);
        fis.close();
    }
}

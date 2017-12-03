import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;

public class SignHandler {

    // JavaIsLife
    private static final String SIGINSTANCE = "SHA1withDSA";
    private KeyStore ks;
    private PrivateKey pk;
    private BufferedInputStream bis;
    private Signature sig;
    private Certificate cert;


    public SignHandler(String[] args) throws Exception {
        loadKeystore(args[0], args[1]);
        loadPrivateKey(args[2], args[3]);
        loadData(args[4]);
        setupCertificate(args[5]);
        setupSignature(args[6]);
        signData(args[4]);
        createCert(args[5], args[2]);
    }

    private void signData(String signaturePath) throws Exception {

    }

    private void createCert(String certFileName, String alias) throws Exception {
        cert = ks.getCertificate(alias);
        FileOutputStream fis = new FileOutputStream(certFileName);
        fis.write(cert.getEncoded());
        fis.close();
    }

    private void loadData(String dataFilePath) throws Exception {
        bis = new BufferedInputStream(new FileInputStream(dataFilePath));
        byte[] inByte = new byte[1024];
        while(bis.available() != 0) {
            int length = bis.read(inByte);
            sig.update(inByte, 0, length);
        }
        bis.close();

    }

    private void loadPrivateKey(String alias, String pkPw) throws Exception {
        // 2 alias - default mykey
        pk = (PrivateKey) ks.getKey(alias, pkPw.toCharArray());

    }

    private void loadKeystore(String ksFile, String ksPw) throws Exception {
        ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream(ksFile), ksPw.toCharArray());

    }

    public static void main(String[] args) {
        try {
            new SignHandler(args);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void setupSignature(String sigFilePath) throws Exception {
        sig = Signature.getInstance(SIGINSTANCE);
        sig.initSign(pk);

    }

    public void setupCertificate(String certFilePath) {
        this.cert = null;
    }
}

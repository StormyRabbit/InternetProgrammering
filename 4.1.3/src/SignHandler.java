import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
        // 4 data
        // 5 cert
        // signature
    }

    private void loadData(String dataFilePath) throws Exception {
        bis = new BufferedInputStream(new FileInputStream(dataFilePath));

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

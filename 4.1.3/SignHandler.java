import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;

public class SignHandler {
    private static final String ENCRYPTION_TYPE = "SHA1withDSA";

    private KeyStore ks;
    private BufferedInputStream bis;
    private FileOutputStream fis;
    private PrivateKey pk;
    private Signature sig;
    private Certificate cert;

    public SignHandler(String[] args) throws Exception {
        String keyStoreName = args[0];
        String keyStorePassword = args[1];
        String keyStoreAlias = args[2];
        String keyPassword = args[3];
        String dataFile = args[4];
        String certFile = args[5];
        String sigFile = args[6];
        loadKey(keyStoreName, keyStoreAlias, keyStorePassword, keyPassword);
        loadSignature(dataFile, sigFile);
        loadCertificate(certFile, keyStoreAlias);

    }

    private void loadCertificate(String certFile, String keyStoreAlias) throws Exception {
        cert = ks.getCertificate(keyStoreAlias);
        byte[] certBytes = cert.getEncoded();
        fis = new FileOutputStream(certFile);
        fis.write(certBytes);
        fis.close();
    }

    private void loadSignature(String data, String sigFile) throws Exception {
        sig = Signature.getInstance(ENCRYPTION_TYPE);
        sig.initSign(pk);
        bis = new BufferedInputStream(new FileInputStream(data));
        byte[] inByte = new byte[1024];
        while(bis.available() != 0) {
            int length = bis.read(inByte);
            sig.update(inByte, 0, length);
        }
        bis.close();
        byte[] sigByte = sig.sign();
        fis = new FileOutputStream(sigFile);
        fis.write(sigByte);
        fis.close();
    }

    private void loadKey(String keystore, String keyStoreAlias, String keyStorePassword, String keyPassword) throws Exception {
        ks = KeyStore.getInstance("JKS");
        bis = new BufferedInputStream(new FileInputStream(keystore));
        ks.load(bis, keyStorePassword.toCharArray());
        pk = (PrivateKey)ks.getKey(keyStoreAlias, keyPassword.toCharArray());
    }

    public static void main(String[] args) {
        try {
            new SignHandler(args);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
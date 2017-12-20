import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class VerifyHandler {
    private static final String SIGNATURE_ENCODING = "SHA1withDSA";
    private FileInputStream fis;
    private BufferedInputStream bis;
    private CertificateFactory certFact;
    private Certificate cert;
    private Signature sig;
    private PublicKey pk;
    private byte[] sigByte;

    public VerifyHandler(String[] args) throws Exception {
        String data = args[0];
        String certFile = args[1];
        String sigFile = args[2];
        loadCertificate(certFile);
        loadSignature(sigFile);
        verifyData(data);
    }

    private void verifyData(String data) throws Exception  {
        bis = new BufferedInputStream(new FileInputStream(data));
        byte[] inByte = new byte[1024];
        while(bis.available() != 0) {
            int length = bis.read(inByte);
            sig.update(inByte, 0, length);
        }
        bis.close();
        if(sig.verify(sigByte))
            System.out.println("DATA VERIFIED!");
        else
            System.out.println("DATA NOT VERIFIED!");
    }

    private void loadSignature(String sigFile) throws Exception {
        sig = Signature.getInstance(SIGNATURE_ENCODING);
        sig.initVerify(pk);
        fis = new FileInputStream(sigFile);
        sigByte = new byte[fis.available()];
        fis.read(sigByte);
        fis.close();
    }

    private void loadCertificate(String certFile) throws Exception {
        fis = new FileInputStream(certFile);
        certFact = CertificateFactory.getInstance("X.509");
        cert = certFact.generateCertificate(fis);
        pk = cert.getPublicKey();
    }

    public static void main(String[] args) {
        try {
            new VerifyHandler(args);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}

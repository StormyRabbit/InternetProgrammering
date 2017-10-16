import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebsiteReader {
    private URL targetURL;
    private StringBuffer sb;
    private BufferedReader br;

    public WebsiteReader() {}

    public void readWebsite(String s) {
        buildURL(s);
        if(targetURL != null)
            parseURL();
    }

    private void buildURL(String s) {
        try {
            targetURL = new URL(s);
        }catch(MalformedURLException mue) {
            System.out.println(mue);
        }

    }

    private void parseURL() {
        sb = new StringBuffer();
        setupBufferedReader();
        String line;
        while( (line = getLine() ) != null) {
            sb.append(line + "\n");
        }
        System.out.println(sb.toString());
    }

    private String getLine() {
        try {
            return br.readLine();
        }catch(IOException ioe) {
            System.out.println(ioe);
        }
        return null;
    }

    private void setupBufferedReader() {
        try {
            br = new BufferedReader(new InputStreamReader(targetURL.openStream()));
        }catch(IOException ioe) {
            System.out.println(ioe);
        }
    }
}

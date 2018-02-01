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
        // takes a string and sends it to the URL builder and then if possible calls the parser.
        buildURL(s);
        if(targetURL != null)
            parseURL();
    }

    private void buildURL(String s) {
        /*
        tries to convert the param String to a URL object.
        */
        try {
            targetURL = new URL(s);
        }catch(MalformedURLException mue) {
            System.out.println(mue);
        }

    }

    private void parseURL() {
        /*
        Takes the previous created URL object and parses homepage.
        */
        sb = new StringBuffer();
        setupBufferedReader();
        String line;
        while( (line = getLine() ) != null) {
            sb.append(line + "\n");
        }
        System.out.println(sb.toString());
    }

    private String getLine() {
        // returns the next line from the bufferedReader
        try {
            return br.readLine();
        }catch(IOException ioe) {
            System.out.println(ioe);
        }
        return null;
    }

    private void setupBufferedReader() {
        /**
         * setup the BufferedReader object and catches any exception.
         */
        try {
            br = new BufferedReader(new InputStreamReader(targetURL.openStream()));
        }catch(IOException ioe) {
            System.out.println(ioe);
        }
    }
}

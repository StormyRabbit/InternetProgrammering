import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {

    private URL url;
    private HttpURLConnection conn;
    private BufferedReader br;
    private BufferedInputStream bis;
    public RestClient() {

    }
}

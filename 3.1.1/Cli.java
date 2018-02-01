import java.util.Scanner;

public class Cli {
    private WebsiteReader wr;
    public Cli() {
        // main constructor of the CLI, creates and binds the reader object and then starts the input loop.
        wr = new WebsiteReader();
        startInputLoop();
    }

    private void startInputLoop() {
        // input loop, that listens for input
        while(true)
            readInput();
    }

    private void readInput() {
        // takes input fomr stdin and sends it to the subprocess responsible.
        Scanner inScan = new Scanner(System.in);
        System.out.print("Input URL:");
        handleInput(inScan.nextLine());
    }

    private void handleInput(String s) {
        // takes a string input and sends it to the WebsiteReader object.
        wr.readWebsite(s);
    }
}

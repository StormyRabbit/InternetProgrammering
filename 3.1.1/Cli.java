import java.util.Scanner;

public class Cli {
    private WebsiteReader wr;
    public Cli() {
        wr = new WebsiteReader();
        startInputLoop();
    }

    private void startInputLoop() {
        while(true)
            readInput();
    }

    private void readInput() {
        Scanner inScan = new Scanner(System.in);
        System.out.print("Input URL:");
        handleInput(inScan.nextLine());
    }

    private void handleInput(String s) {
        wr.readWebsite(s);
    }
}

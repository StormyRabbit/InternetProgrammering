import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;

public class XMLChatInbox extends Thread {
    private BufferedReader chatReader;
    private boolean running;

    public XMLChatInbox(Socket s) throws Exception {
        // main constructor, binds and creates the reader object and starts the mainloop.
        chatReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        running = true;
        this.start();
    }

    @Override
    public void run() {
        // main thread for incoming msgs. Verifies the XML format and prints the msg.
        while(running) {
            try {
                SAXBuilder sb = new SAXBuilder();
                String s = chatReader.readLine();
                Document d = sb.build(new StringReader(s));
                Format prettyFormat = Format.getPrettyFormat();
                XMLOutputter output = new XMLOutputter(prettyFormat);
                System.out.printf("%s (%s): %s",
                        d.getRootElement().getChild("header").getChild("id").getChild("name").getText(),
                        d.getRootElement().getChild("header").getChild("id").getChild("email").getText(),
                        d.getRootElement().getChild("body").getText());
                System.out.printf("\n\nINCOMING XML FORMAT: \n %s", output.outputString(d));
            }catch(Exception e) {
                System.out.printf("KUNDE INTE PARSA: \n %s", e.toString());
            }
        }
    }
}

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class XMLChatClient {
    private Socket s;
    private static String ip = "atlas.dsv.su.se";
    private static int port = 9494;
    private String name;
    private String email;
    private Scanner scan;
    private PrintWriter outWriter;
    private XMLChatInbox inbox;
    public XMLChatClient(String name, String email) throws Exception {
        s = new Socket(ip, port);
        this.name = name;
        this.email = email;
        inbox = new XMLChatInbox(s);
        outWriter = new PrintWriter(s.getOutputStream());
        startCLI();
    }

    private void startCLI() {
        scan = new Scanner(System.in);
        while(true) {
            System.out.print("Enter message to send:");
            String msgBody = scan.nextLine();
            Document outDoc = testXML(msgBody);
            Format form = Format.getCompactFormat();
            XMLOutputter outputter = new XMLOutputter(form);
            outWriter.println(outputter.outputString(outDoc));
        }

    }

    private Document testXML(String msgBody) {
        Element msg = new Element("message");
        Element header = new Element("header");
        Element protocol = new Element("protocol");
        Element type = new Element("type");
        Element version = new Element("version");
        Element cmd = new Element("command");
        Element id = new Element("id");
        Element name = new Element("name");
        Element email = new Element("email");
        Element homepage = new Element("homepage");
        Element host = new Element("host");
        Element body = new Element("body");
        msg.addContent(body);
        msg.addContent(header);
        header.addContent(protocol);
        header.addContent(id);
        protocol.addContent(type);
        protocol.addContent(version);
        protocol.addContent(cmd);
        id.addContent(name);
        id.addContent(email);
        id.addContent(homepage);
        id.addContent(host);
        type.addContent("CTTP");
        version.addContent("1.0");
        cmd.addContent("MESS");
        name.addContent(this.name);
        email.addContent(this.email);
        body.addContent(msgBody);

        DocType dt = new DocType("message",
                "1//PW//Example//123",
                "https://atlas.dsv.su.se/~pierre/courses/05_ass/ip1/2/2.1.3/message.dtd");
        Document d = new Document(msg, dt);
        XMLOutputter xmlOut = new XMLOutputter();

        return d;
    }


}

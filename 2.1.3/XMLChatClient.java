import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class XMLChatClient {
    private Socket s;
    private String ip = "atlas.dsv.su.se";
    private int port = 9494;
    private String name;
    private String email;
    private String homepage;
    private Scanner scan;
    private PrintWriter outWriter;
    private XMLChatInbox inbox;
    public XMLChatClient() throws Exception {
        initiateCommunication();
        readInUserInfo();
        startCLI();
    }

    private void initiateCommunication() throws Exception {
        s = new Socket(ip, port);
        inbox = new XMLChatInbox(s);
        outWriter = new PrintWriter(s.getOutputStream(), true);
        scan = new Scanner(System.in);
    }

    private void readInUserInfo() {
        System.out.println("Enter name:");
        name = scan.nextLine();
        System.out.println("Enter E-mail address");
        email = scan.nextLine();
        System.out.println("Enter homepage address");
        homepage = scan.nextLine();
    }

    private void startCLI() {
        while(true) {
            System.out.print("Enter message to send:");
            String msgBody = scan.nextLine();
            System.out.println(msgBody);
            Document outDoc = createDocument(msgBody);
            Format form = Format.getCompactFormat();
            form.setLineSeparator("");
            XMLOutputter outPutter = new XMLOutputter(form);
            outWriter.println(outPutter.outputString(outDoc));
            Format prettyFormat = Format.getPrettyFormat();
            XMLOutputter output = new XMLOutputter(prettyFormat);
            System.out.printf("\n\nOUTGOING XML FORMAT: \n %s", output.outputString(outDoc));
        }

    }

    private Document createDocument(String msgBody) {
        DocType dt = new DocType("message",
                "1//PW//Example//123",
                "https://people.dsv.su.se/~pierre/courses/05_ass/ip1/2/2.1.3/message.dtd");

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
        msg.addContent(header);
        msg.addContent(body);
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
        homepage.addContent(this.homepage);
        host.addContent("unknown");
        body.addContent(msgBody);

        return new Document(msg, dt);
    }


}

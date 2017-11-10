import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Queue;

public class MailClient {

    private String mailServer;
    private Properties props;
    private String port;
    private Session ses;
    private Queue<EmailMsg> msgQueue;
    private Authenticator auth;
    public MailClient() {
        // utveckla till getInstance() istället?
    }

    private void setupAuthentication() {
        auth = new EmailAuthenticator();
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;

    }

    public void setPort(String port) {
        this.port = port;

    }

    private void setupProperties() {
        props = System.getProperties();
        if(!isConfigured())
            setDefaultProperties();
        setProperties();
    }

    private boolean isConfigured() {
        return mailServer == null || port == null;
    }

    private void setProperties() {
        props.put("mail.smtp.host", mailServer);
        props.put("mail.smtp.port", port);

    }

    private void setDefaultProperties() {
        mailServer = "stmp.gmail.com";
        port = "465";

    }

    public void addEmailToQueue(EmailMsg eMsg) {

    }

    public void sendAllQueuedMsgs() {
        while(!msgQueue.isEmpty())
            sendMessage(msgQueue.poll());
    }

    private void sendMessage(EmailMsg msg) {
        MimeMessage message = new MimeMessage(ses);
        try {
            message.setFrom(new InternetAddress(msg.getFrom()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(msg.getTo()));
            message.setSubject(msg.getSubject());
            message.setText(msg.getMsgBody());
            Transport.send(message);
        }catch(MessagingException me) {
            System.out.println(me);
        }
    }

}

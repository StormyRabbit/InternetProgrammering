
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.regex.*;

public class Gui extends JFrame {


    // new message panel elements
    private JPanel newMsgPanel;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel homepageLabel;
    private JLabel messageLabel;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField homepageField;
    private JTextArea inMessageArea;
    private JButton postMsgButton;

    // old message panel elements
    private JPanel allEntriesPanel;
    private JTextArea allEntriesTextArea;
    private List<GuestBookEntry> gbEntryList;

    // rest
    private MySQLManager mySQL;

    private Pattern p = Pattern.compile("<.*>");
    public Gui () {
        /**
         * main constructor imports the db driver, creates the UI
         * and binds and creates the needed objects.
         */
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {}
        this.setLayout(new BorderLayout());
        setupNewEntryPanel();
        setupAllEntriesPanel();
        mySQL = new MySQLManager();
        gbEntryList = mySQL.getAllEntries();
        fillGuestBookTextArea();
        this.pack();
        this.setVisible(true);
    }

    private void setupNewEntryPanel() {
        /**
         *  setups all the element for the JPanel responsible for new entries.
         */
        newMsgPanel = new JPanel();
        nameLabel = new JLabel("Name");
        emailLabel = new JLabel("E-mailadress");
        homepageLabel = new JLabel("Homepage");
        messageLabel = new JLabel("Message");
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        homepageField = new JTextField(20);
        inMessageArea = new JTextArea();
        postMsgButton = new JButton("Post");
        newMsgPanel.add(nameLabel);
        newMsgPanel.add(nameField);
        newMsgPanel.add(emailLabel);
        newMsgPanel.add(emailField);
        newMsgPanel.add(homepageLabel);
        newMsgPanel.add(homepageField);
        newMsgPanel.add(messageLabel);
        newMsgPanel.add(inMessageArea);
        newMsgPanel.add(postMsgButton);
        nameField.setMaximumSize( nameField.getPreferredSize() );
        homepageField.setMaximumSize(homepageField.getPreferredSize());
        emailField.setMaximumSize( emailField.getPreferredSize());
        postMsgButton.addActionListener(e -> {

            mySQL.postNewEntry(createGBE());
            refreshGuestbook();

        });
        newMsgPanel.setLayout(new BoxLayout(newMsgPanel, BoxLayout.PAGE_AXIS));
        this.add(newMsgPanel, BorderLayout.WEST);
    }

    private GuestBookEntry createGBE() {
        // creates a new guestBookEntry object using data from the input elements.
        GuestBookEntry gbe = new GuestBookEntry();

        gbe.setName(scrubString(nameField.getText()));
        gbe.setEmail(scrubString(emailField.getText()));
        gbe.setHomepage(scrubString(homepageField.getText()));
        gbe.setMessage(scrubString(inMessageArea.getText()));
        return gbe;
    }

    private String scrubString(String s) {
        // used for scrubbing inputs from html kod.
        Matcher matcher = p.matcher(s);
        if(matcher.matches())
            return "censur";
        else return s;
    }

    private void refreshGuestbook() {
        // refreshes the list of guestbook entries.
        allEntriesTextArea.setText("");
        gbEntryList = mySQL.getAllEntries();
        fillGuestBookTextArea();
    }

    private void fillGuestBookTextArea() {
        // writes all the stored entries to the textArea
        for(GuestBookEntry gbe : gbEntryList)
            allEntriesTextArea.append(gbe.toString());
    }

    private void setupAllEntriesPanel() {
        // creates and binds all the objects used for the JPanel hosting all existing entries.
        allEntriesPanel = new JPanel();
        allEntriesTextArea = new JTextArea();
        allEntriesPanel.setLayout(new BorderLayout());
        allEntriesPanel.add(allEntriesTextArea, BorderLayout.CENTER);
        allEntriesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(allEntriesPanel, BorderLayout.CENTER);

    }
}

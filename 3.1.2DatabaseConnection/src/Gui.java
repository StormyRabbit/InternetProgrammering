
import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    public Gui () {
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
        GuestBookEntry gbe = new GuestBookEntry();
        gbe.setName(nameField.getText());
        gbe.setEmail(emailField.getText());
        gbe.setHomepage(homepageField.getText());
        gbe.setMessage(inMessageArea.getText());
        return gbe;
    }

    private void refreshGuestbook() {
        allEntriesTextArea.setText("");
        gbEntryList = mySQL.getAllEntries();
        fillGuestBookTextArea();
    }

    private void fillGuestBookTextArea() {
        for(GuestBookEntry gbe : gbEntryList)
            allEntriesTextArea.append(gbe.toString());
    }

    private void setupAllEntriesPanel() {
        allEntriesPanel = new JPanel();
        allEntriesTextArea = new JTextArea();
        allEntriesPanel.setLayout(new BorderLayout());
        allEntriesPanel.add(allEntriesTextArea, BorderLayout.CENTER);
        allEntriesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(allEntriesPanel, BorderLayout.CENTER);

    }
}

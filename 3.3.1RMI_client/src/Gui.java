import javax.swing.*;
public class Gui extends JFrame {

    private RmiClient rmic;

    public Gui() {
        try {
            rmic = new RmiClient();
        }catch(Exception e) {
            System.out.println(e);
        }
        this.setVisible(true);
    }

}

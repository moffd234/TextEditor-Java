import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AboutDialog extends Dialog implements ActionListener {
    AboutDialog(Frame parent, String title) {
        super(parent, title, false);
        this.setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(500, 300);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose(); // Deletes all window items and returns the memory to the OS
    }
}
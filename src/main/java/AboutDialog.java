import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AboutDialog extends JDialog implements ActionListener {
    AboutDialog(Frame parent, String title) {
        super(parent, title, false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // When window is closed dispose() gets called
        this.setResizable(true);
        setLayout(new BorderLayout());
        setSize(500, 300);


        // Create text area
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Roboto", Font.PLAIN, 12));
        textArea.setText("About This Text Editor\n" +
                "- Made by a bootcamp student" +
                "- Made using Java and Swing");


        JScrollPane scrPane = new JScrollPane(textArea);  // Create a scroll pane containing the textArea
        add(scrPane, BorderLayout.CENTER); // Adds the JScrollPane and centers it
    }

    public void actionPerformed(ActionEvent ae) {
        dispose(); // Deletes all window items and returns the memory to the OS
    }
}

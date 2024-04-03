import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HelpDocs extends JDialog implements ActionListener {
    HelpDocs(Frame parent, String title) {
        super(parent, title, false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // When window is closed dispose() gets called
        this.setResizable(true);
        setLayout(new BorderLayout());
        setSize(500, 300);


        // Create text area
        JTextArea textArea = new JTextArea();
        textArea.setText("HELP DOCUMENTATION\n" +
                "Text Editor Help Documentation\n\n" +
                "1. File Menu:\n" +
                "   - New: Create a new document.\n" +
                "   - Open: Open an existing document.\n" +
                "   - Save: Save the current document.\n" +
                "   - Save As: Save the current document with a new name.\n" +
                "   - Page Setup: Configure page settings for printing.\n" +
                "   - Print: Print the document.\n" +
                "   - Exit: Close the application.\n\n" +
                "2. Edit Menu:\n" +
                "   - Delete: Delete selected text.\n" +
                "   - Cut: Cut selected text.\n" +
                "   - Copy: Copy selected text.\n" +
                "   - Paste: Paste copied/cut text.\n" +
                "   - Find: Find text in the document.\n" +
                "   - Find Next: Find the next occurrence of the searched text.\n" +
                "   - Replace: Replace text in the document.\n" +
                "   - Go To: Navigate to a specific line number.\n" +
                "   - Select All: Select all text in the document.\n" +
                "   - Time Stamp: Insert current date and time.\n\n" +
                "3. Tools Menu:\n" +
                "   - Choose Font: Change the font of the text.\n" +
                "   - Compile: Compile the code (if applicable).\n" +
                "   - Run: Execute the code (if applicable).\n\n" +
                "4. Help Menu:\n" +
                "   - Help Topics: Brings you here.\n" +
                "   - About TextEditor: View information about the text editor.\n");


        JScrollPane scrPane = new JScrollPane(textArea);  // Create a scroll pane containing the textArea
        add(scrPane, BorderLayout.CENTER); // Adds the JScrollPane and centers it
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
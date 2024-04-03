import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
TODO - find/replace text implementation
TODO - add a new window, scrollable, with some editor help documentation in it.
TODO - implement Word Wrap (yeah, look it up)
TODO - add a font panel, so you can change the font you edit in.
TODO - make sure not only new files are in the new font, but existing windows too.
TODO - add a way to set the page in portrait or landscape mode
TODO - Make your About Dialog snazzy!
 */

//TextEditor class starts here
class TextEditor extends Frame implements ActionListener {
    Logger logger = Logger.getLogger(TextEditor.class.getName());

    JTextArea ta = new JTextArea();
    int i, len1, len, pos1;
    String str = "", s3 = "", s2 = "", s4 = "", filePath = "", s6 = "", s7 = "", s8 = "";

    // A list of months (Not sure what this is used for yet)
    String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
    // Creates a checkBox titled "Word Wrap"
    CheckboxMenuItem checkboxItem = new CheckboxMenuItem("Word Wrap");

    public TextEditor() {

        // Creates a menuBar at the top of the screen
        MenuBar mb = new MenuBar();
        setLayout(new BorderLayout());
        add("Center", ta);
        setMenuBar(mb);

        // Create menu bar options
        Menu m1 = new Menu("File");
        Menu m2 = new Menu("Edit");
        Menu m3 = new Menu("Tools");
        Menu m4 = new Menu("Help");

        // Add the menu bar options to the menu bar
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);

        // Create submenu under File
        MenuItem[] mi1 = {
                new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
                new MenuItem("Page Setup"), new MenuItem("Print"), new MenuItem("Exit")
        };
        // Create submenu under Edit
        MenuItem[] mi2 = { new MenuItem("Delete"), new MenuItem("Cut"),
                new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Find"),
                new MenuItem("Find Next"), new MenuItem("Replace"),
                new MenuItem("Go To"), new MenuItem("Select All"),
                new MenuItem("Time Stamp") };
        // Create submenu under Tools
        MenuItem[] mi3 = { new MenuItem("Choose Font"), new MenuItem("Compile"),
                new MenuItem("Run"), new MenuItem("Print") };
        // Create submenu under Health
        MenuItem[] mi4 = { new MenuItem("Help Topics"),
                new MenuItem("About TextEditor") };

        // Iterate through the File submenu and add actionListeners for each item
        // in the submenu
        for (MenuItem menuItem : mi1) {
            m1.add(menuItem);
            menuItem.addActionListener(this);
        }

        // Iterate through the Edit submenu and add actionListeners for each item
        // in the submenu
        for (MenuItem menuItem : mi2) {
            m2.add(menuItem);
            menuItem.addActionListener(this);
        }

        // Adds checkboxItem to Tools submenu
        m3.add(checkboxItem);
        checkboxItem.addActionListener(this);

        // Iterate through the Tools submenu and add actionListeners for each item
        // in the submenu
        for (MenuItem menuItem : mi3) {
            m3.add(menuItem);
            menuItem.addActionListener(this);
        }

        // Iterate through the Help submenu and add actionListeners for each item
        // in the submenu
        for (MenuItem menuItem : mi4) {
            m4.add(menuItem);
            menuItem.addActionListener(this);
        }

        // Creates a window adapted?
        MyWindowsAdapter mw = new MyWindowsAdapter(this);
        addWindowListener(mw);
        setSize(500, 500); // Sets size of window
        setTitle("untitled notepad"); // Sets window title
        setVisible(true); // Makes window visible
    }

    public void actionPerformed(ActionEvent ae) {
        String arg = (String) ae.getActionCommand();
        System.out.println(arg);
        if (arg.equals("New")) {
            dispose(); // Deletes all window items and returns the memory to the OS
            TextEditor t11 = new TextEditor();
            t11.setSize(500, 500);
            t11.setVisible(true);
        }

        try { // Try to open a new file then display it
            if (arg.equals("Open")) {
                // THE FOLLOWING THROWS AN IOEXCEPTION IF THE USER HITS CANCEL IN THE DIALOG
                openAndDisplayFile();
            }
        } catch (IOException e) {
            // Log the error's stack trace
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        try {
            if (arg.equals("Save")) {
                saveCurrentFile();
            }
        } catch (IOException e){
            // Log the error's stack trace
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        try {
            // Save a file and prompt user what to save the file as
            if (arg.equals("Save As")) {
                saveFileAs();
            }
            this.setTitle(s8 + " TextEditor File"); // Titles the window the same as the file name
        } catch (IOException e) {
            // Log the error's stack trace
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        if (arg.equals("Exit")) {
           try {
                saveCurrentFile();
            } catch (IOException e) {
                // Log the error's stack trace
                logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }
            System.exit(0);
        }
        if (arg.equals("Cut")) {
            str = ta.getSelectedText();
            i = ta.getText().indexOf(str);
            ta.replaceRange(" ", i, i + str.length());
        }
        if (arg.equals("Copy")) {
            str = ta.getSelectedText();
        }
        if (arg.equals("Paste")) {
            pos1 = ta.getCaretPosition();
            ta.insert(str, pos1);
        }
        if (arg.equals("Delete")) {
            String msg = ta.getSelectedText();
            i = ta.getText().indexOf(msg);
            ta.replaceRange(" ", i, i + msg.length());
            msg = "";
        }
        if (arg.equals("Select All")) {
            String strText = ta.getText();
            int strLen = strText.length();
            ta.select(0, strLen);
        }
        if (arg.equals("Time Stamp")) {
            GregorianCalendar gcalendar = new GregorianCalendar();
            String h = String.valueOf(gcalendar.get(Calendar.HOUR));
            String m = String.valueOf(gcalendar.get(Calendar.MINUTE));
            String s = String.valueOf(gcalendar.get(Calendar.SECOND));
            String date = String.valueOf(gcalendar.get(Calendar.DATE));
            String mon = months[gcalendar.get(Calendar.MONTH)];
            String year = String.valueOf(gcalendar.get(Calendar.YEAR));
            String hms = "Time" + " - " + h + ":" + m + ":" + s + " Date" + " - " + date + " " + mon + " " + year + " ";
            int loc = ta.getCaretPosition();
            ta.insert(hms, loc);
        }
        if (arg.equals("Word Wrap")){
            // Doesn't actually wrap the words for some reason... will come back to this
            boolean isChecked = checkboxItem.getState();
            System.out.println("Word wrap is " + isChecked);
            ta.setLineWrap(isChecked);
            ta.setWrapStyleWord(true);
            ta.repaint();
        }
        if (arg.equals("Print")){
            try {
                ta.print(); // Shows a print dialog (Not tested right now due to lack of printer availability)
            } catch (PrinterException e) {
                // Log the error's stack trace
                logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }
        }
        if (arg.equals("About TextEditor")) {
            AboutDialog d1 = new AboutDialog(this, "About TextEditor");
            d1.setVisible(true);
            setSize(500, 500);
        }
        if (arg.equals("Help Topics")){
            HelpDocs hd = new HelpDocs(this, "Help Documentation");
            hd.setVisible(true);
            setSize(500, 500);
        }
    }

    private void saveCurrentFile() throws IOException {
        s6 = ta.getText(); // Gets the text from the textArea
        len1 = s6.length(); // Gets the length of the text that's in the textArea
        byte[] buf = s6.getBytes(); // Convert the text to bytes
        if(!Objects.equals(filePath, "")) {
            // ASSERT: A file path has already been established because the user either already used
            //         Open or Save
            if(!filePath.isEmpty()) {
                writeBufferToFile(buf, filePath); // Writes the buffer to the file at the filePath or s9
            }
            else {
                writeBufferToFile(buf, filePath);
            }
        }
        else{
            saveFileAs();
        }
    }

    private void writeBufferToFile(byte[] buf, String fp) throws IOException {
        File f1 = new File(fp); // Creates a new file object from the filePath
        // created in getSaveAsDirectory
        FileOutputStream fobj1 = new FileOutputStream(f1); // Creates FileOutputStream object from the file
        // Writes the bytes to the file
        for (int k = 0; k < len1; k++) {
            fobj1.write(buf[k]);
        }
        fobj1.close(); // Close the FileOutputStream
    }

    private void saveFileAs() throws IOException {
        getSaveAsDirectory(); // Prompts the user to select where to save the file
                              // And what to save the file as. Then creates a filePath from the info
        s6 = ta.getText(); // Gets the text from the textArea
        len1 = s6.length(); // Gets the length of the text that's in the textArea
        byte[] buf = s6.getBytes(); // Convert the text to bytes
        File f1 = new File(filePath); // Creates a new file object from the filePath
                                // created in getSaveAsDirectory
        FileOutputStream fobj1 = new FileOutputStream(f1); // Creates FileOutputStream object from the file

        // Writes the bytes to the file
        for (int k = 0; k < len1; k++) {
            fobj1.write(buf[k]);
        }
        fobj1.close(); // Close the FileOutputStream
    }

    private void getSaveAsDirectory() {
        // Creates a file dialog for saving the file
        FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
        dialog1.setVisible(true); // Displays the dialog
        s7 = dialog1.getDirectory(); // Gets the directory of where the file will be saved
        s8 = dialog1.getFile(); // Get the fileName of the file to be saved
        filePath = s7 + s8 + ".txt"; // Create the filePath of the file to be saved and make it a text file
    }

    private void openAndDisplayFile() throws IOException {
        // Creates a file dialog window so user can select a file to open
        FileDialog fd1 = new FileDialog(this, "Select File", FileDialog.LOAD);
        fd1.setVisible(true); // Displays the file dialog

        String s4 = ""; // Creates an empty string
        createFilePath(fd1); // Gets the filePath of the selected file

        File f = new File(filePath); // Creates new file object with the filePath in s32
        FileInputStream fii = new FileInputStream(f); // Creates a fileInputStream from the file
        len = (int) f.length(); // Gets the length of the file

        s4 = getStringToDisplay(fii, s4); // Iterate through the contents of the file character by character
                                          // And add it to s4

        ta.setText(s4); // Sets textArea to the contents of the file
        fii.close(); // Closes the FileInputStream object
    }

    private void createFilePath(FileDialog fd1) {
        s2 = fd1.getFile(); // Gets the fileName of the selected file
        s3 = fd1.getDirectory(); // Gets the directory of the selected file
        filePath = s3 + s2; // Combines the fileName and directory to create a file path
    }

    private String getStringToDisplay(FileInputStream fii, String s4) throws IOException {
        for (int j = 0; j < len; j++) {
            char s5 = (char) fii.read(); // Gets current character
            s4 += s5; // Adds the current character to the s4 string (Starts as an empty string)
        }
        return s4;
    }

    public static void main(String[] args) {
        TextEditor to = new TextEditor();
        System.out.println(to);
    }
}

class MyWindowsAdapter extends WindowAdapter {
    TextEditor tt;

    public MyWindowsAdapter(TextEditor ttt) {
        tt = ttt;
    }

    public void windowClosing(WindowEvent we) {
        tt.dispose(); // Deletes all window items and returns the memory to the OS
    }
}
class HelpDocs extends Dialog implements ActionListener{
    HelpDocs(Frame parent, String title) {
        super(parent, title, false);
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

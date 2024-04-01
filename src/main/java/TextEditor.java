import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//TextEditor class starts here
class TextEditor extends Frame implements ActionListener {
    Logger logger = Logger.getLogger(TextEditor.class.getName());

    TextArea ta = new TextArea();
    int i, len1, len, pos1;
    String str = "", s3 = "", s2 = "", s4 = "", s32 = "", s6 = "", s7 = "", s8 = "", s9 = "";

    // A list of months (Not sure what this is used for yet
    String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
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
        MenuItem mi1[] = {
                new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
                new MenuItem("Page Setup"), new MenuItem("Print"), new MenuItem("Exit")
        };
        // Create submenu under Edit
        MenuItem mi2[] = { new MenuItem("Delete"), new MenuItem("Cut"),
                new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Find"),
                new MenuItem("Find Next"), new MenuItem("Replace"),
                new MenuItem("Go To"), new MenuItem("Select All"),
                new MenuItem("Time Stamp") };
        // Create submenu under Tools
        MenuItem mi3[] = { new MenuItem("Choose Font"), new MenuItem("Compile"),
                new MenuItem("Run") };
        // Create submenu under Health
        MenuItem mi4[] = { new MenuItem("Help Topics"),
                new MenuItem("About TextEditor") };

        // Iterate through the File submenu and add actionListeners for each item
        // in the submenu
        for (int i = 0; i < mi1.length; i++) {
            m1.add(mi1[i]);
            mi1[i].addActionListener(this);
        }

        // Iterate through the Edit submenu and add actionListeners for each item
        // in the submenu
        for (int i = 0; i < mi2.length; i++) {
            m2.add(mi2[i]);
            mi2[i].addActionListener(this);
        }

        // Adds checkboxItem to Tools submenu
        m3.add(checkboxItem);
        checkboxItem.addActionListener(this);

        // Iterate through the Tools submenu and add actionListeners for each item
        // in the submenu
        for (int i = 0; i < mi3.length; i++) {
            m3.add(mi3[i]);
            mi3[i].addActionListener(this);
        }

        // Iterate through the Help submenu and add actionListeners for each item
        // in the submenu
        for (int i = 0; i < mi4.length; i++) {
            m4.add(mi4[i]);
            mi4[i].addActionListener(this);
        }

        // Creates a window adapted? (Expand(
        MyWindowsAdapter mw = new MyWindowsAdapter(this);
        addWindowListener(mw);
        setSize(500, 500); // Sets size of window
        setTitle("untitled notepad"); // Sets window title
        setVisible(true); // Makes window visible
    }

    public void actionPerformed(ActionEvent ae) {
        String arg = (String) ae.getActionCommand();
        if (arg.equals("New")) {
            dispose();
            TextEditor t11 = new TextEditor();
            t11.setSize(500, 500);
            t11.setVisible(true);
        }
        try { // Try to open a new file then display it
            // Open a new file
            if (arg.equals("Open")) {
                // Creates a file dialog window so user can select a file to open
                FileDialog fd1 = new FileDialog(this, "Select File", FileDialog.LOAD);
                fd1.setVisible(true); // Displays the file dialog
                String s4 = ""; // Creates an empty string
                s2 = fd1.getFile(); // Gets the fileName of the selected file
                s3 = fd1.getDirectory(); // Gets the directory of the selected file
                s32 = s3 + s2; // Combines the fileName and directory to create a file path
                File f = new File(s32); // Creates new file object with the filePath in s32
                FileInputStream fii = new FileInputStream(f); // Creates a fileInputStream from the file
                len = (int) f.length(); // Gets the length of the file
                // Iterate through the contents of the file character ny character
                for (int j = 0; j < len; j++) {
                    char s5 = (char) fii.read(); // Gets current character
                    s4 += s5; // Adds the current character to the s4 string (Starts as an empty string)
                }
                ta.setText(s4); // Sets textArea to the contents of the file
                fii.close(); // Closes the FileInputStream object
            }
        } catch (IOException e) {
            // Log the error's stack trace
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        // TODO - Continue writing comments from here down
        try {
            // Save a file and prompt user what to save the file as
            if (arg.equals("Save As")) {
                FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);
                s7 = dialog1.getDirectory();
                s8 = dialog1.getFile();
                s9 = s7 + s8 + ".txt";
                s6 = ta.getText();
                len1 = s6.length();
                byte buf[] = s6.getBytes();
                File f1 = new File(s9);
                FileOutputStream fobj1 = new FileOutputStream(f1);
                for (int k = 0; k < len1; k++) {
                    fobj1.write(buf[k]);
                }
                fobj1.close();
            }
            this.setTitle(s8 + " TextEditor File");
        } catch (IOException e) {
        }
        if (arg.equals("Exit")) {

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
        if (arg.equals("About TextEditor")) {
            AboutDialog d1 = new AboutDialog(this, "About TextEditor");
            d1.setVisible(true);
            setSize(500, 500);
        }
    }
    public static void main(String args[]) {
        TextEditor to = new TextEditor();
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

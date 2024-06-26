import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


//TextEditor class starts here
class TextEditor extends Frame implements ActionListener, ItemListener {
    Logger logger = Logger.getLogger(TextEditor.class.getName());

    JTextArea ta = new JTextArea();
    int i, len1, len, pos1;
    String str = "", s3 = "", s2 = "", filePath = "", s6 = "", s7 = "", s8 = "";

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

        // Create menuItems under File
        MenuItem[] mi1 = {
                new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"),
                new MenuItem("Save As"), new MenuItem("Page Setup"), new MenuItem("Print"),
                new MenuItem("Exit")
        };

        // Create menuItems under Edit
        MenuItem[] mi2 = { new MenuItem("Delete"), new MenuItem("Cut"),
                new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Find"),
                new MenuItem("Find Next"), new MenuItem("Replace"),
                new MenuItem("Go To"), new MenuItem("Select All"),
                new MenuItem("Time Stamp")};

        Menu fonts = new Menu("Fonts");  // Creates fonts submenu
        String[] allFonts = getFonts(); // Get all available fonts
        MenuItem[] fontsMenuItems = new MenuItem[allFonts.length]; // Create list of menuItems

        // Iterate through each font
        for(int i = 0; i < allFonts.length; i++){
            fontsMenuItems[i] = new MenuItem(allFonts[i]); // Create a new menuItem for the current font
            fontsMenuItems[i].addActionListener(this); // Add an action listener to the menuItem
            fonts.add(fontsMenuItems[i]); // Add the menuItem to the fonts menu
        }

        // Create menuItems under Tools
        MenuItem[] mi3 = { fonts, new MenuItem("Compile"),
                new MenuItem("Run"), new MenuItem("Print") };

        // Create menuItems under Help
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
        checkboxItem.addItemListener(this);

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
        String arg = ae.getActionCommand();

        for(String font : getFonts()){
            if(arg.equals(font)){
                ta.setFont(new Font(font, Font.PLAIN, 12));
            }
        }
        if (arg.equals("New")) {
            Font font = ta.getFont(); // Get the current font so we can persist it
            dispose(); // Deletes all window items and returns the memory to the OS
            TextEditor t11 = new TextEditor();
            t11.ta.setFont(font); // Set the font to what it was in the old window
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
        }
        if (arg.equals("Select All")) {
            String strText = ta.getText();
            int strLen = strText.length();
            ta.select(0, strLen);
        }
        if (arg.equals("Time Stamp")) {
            String hms = getString();
            int loc = ta.getCaretPosition();
            ta.insert(hms, loc);
        }
        if(arg.equals("Find")){
            find();
        }
        if (arg.equals("Replace")){
            findAndReplace();
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

    private String getString() {
        GregorianCalendar gCalendar = new GregorianCalendar();
        String h = String.valueOf(gCalendar.get(Calendar.HOUR));
        String m = String.valueOf(gCalendar.get(Calendar.MINUTE));
        String s = String.valueOf(gCalendar.get(Calendar.SECOND));
        String date = String.valueOf(gCalendar.get(Calendar.DATE));
        String mon = months[gCalendar.get(Calendar.MONTH)];
        String year = String.valueOf(gCalendar.get(Calendar.YEAR));
        return "Time" + " - " + h + ":" + m + ":" + s + " Date" + " - " + date + " " + mon + " " + year + " ";
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == checkboxItem) {
            // Checkbox state changed, do something
            boolean isChecked = checkboxItem.getState();
            ta.setLineWrap(isChecked);
            ta.setWrapStyleWord(true);
            ta.repaint();
        }
    }

    private void saveCurrentFile() throws IOException {
        s6 = ta.getText(); // Gets the text from the textArea
        len1 = s6.length(); // Gets the length of the text that's in the textArea
        byte[] buf = s6.getBytes(); // Convert the text to bytes
        if(!Objects.equals(filePath, "")) {
            // ASSERT: A file path has already been established because the user either already used
            //         Open or Save
            writeBufferToFile(buf, filePath); // Writes the buffer to the file at the filePath or s9
        }
        else{
            saveFileAs(); // Prompt the user to choose where to save the file to
        }
    }

    private void writeBufferToFile(byte[] buf, String fp) throws IOException {
        File f1 = new File(fp); // Creates a new file object from the filePath
        // created in getSaveAsDirectory
        FileOutputStream fObj1 = new FileOutputStream(f1); // Creates FileOutputStream object from the file
        // Writes the bytes to the file
        for (int k = 0; k < len1; k++) {
            fObj1.write(buf[k]);
        }
        fObj1.close(); // Close the FileOutputStream
    }

    private void saveFileAs() throws IOException {
        getSaveAsDirectory(); // Prompts the user to select where to save the file
                              // And what to save the file as. Then creates a filePath from the info
        s6 = ta.getText(); // Gets the text from the textArea
        len1 = s6.length(); // Gets the length of the text that's in the textArea
        byte[] buf = s6.getBytes(); // Convert the text to bytes
        File f1 = new File(filePath); // Creates a new file object from the filePath
                                // created in getSaveAsDirectory
        FileOutputStream fObj1 = new FileOutputStream(f1); // Creates FileOutputStream object from the file

        // Writes the bytes to the file
        for (int k = 0; k < len1; k++) {
            fObj1.write(buf[k]);
        }
        fObj1.close(); // Close the FileOutputStream
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
    private void findAndReplace(){
        String findText = JOptionPane.showInputDialog("Enter find text");
        String replaceText = JOptionPane.showInputDialog("Enter replacement text");
        ta.setText(replace(findText, replaceText));
        ta.repaint();
    }

    private String replace(String textToBeReplaced, String replaceWithThis){
        String textAreaText = ta.getText();
        return textAreaText.replace(textToBeReplaced, replaceWithThis);
    }

    private void find(){
        String findText = JOptionPane.showInputDialog("Enter find text");
        if(findText != null) {
            int numOccurrences = countOccurrencesInString(findText, ta.getText());
            JOptionPane.showMessageDialog(this,
                    findText + " occurs  " + numOccurrences + " times");
        }
    }

    private int countOccurrencesInString(String toFind, String fullString){
        toFind = toFind.toLowerCase();
        fullString = fullString.toLowerCase();

        int count = 0;
        int position = 0;
        int index;
        while((index = fullString.indexOf(toFind, position)) != -1){
            count += 1;
            position = index + toFind.length();
        }
        return count;
    }
    
    public String[] getFonts(){
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    public static void main(String[] args) {
        TextEditor to = new TextEditor();
        System.out.println(to);
    }
}




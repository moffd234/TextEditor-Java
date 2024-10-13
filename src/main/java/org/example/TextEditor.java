package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

//TextEditor class starts here
class TextEditor extends Frame implements ActionListener {
    TextArea ta = new TextArea();
    int i, len1, len, pos1;
    String str = "";
    String s3 = "";
    String s2 = "";
    String s32 = "";
    String s6 = "";
    String s7 = "";
    String s8 = "";
    String s9 = "";
    String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
    CheckboxMenuItem checkBox = new CheckboxMenuItem("Word Wrap");

    public TextEditor() {
        MenuBar mb = new MenuBar();
        setLayout(new BorderLayout());
        add("Center", ta);
        setMenuBar(mb);
        Menu m1 = new Menu("File");
        Menu m2 = new Menu("Edit");
        Menu m3 = new Menu("Tools");
        Menu m4 = new Menu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);
        MenuItem[] mi1 = {
                new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
                new MenuItem("Page Setup"), new MenuItem("Print"), new MenuItem("Exit")
        };
        MenuItem[] mi2 = { new MenuItem("Delete"), new MenuItem("Cut"),
                new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Find"),
                new MenuItem("Find Next"), new MenuItem("Replace"),
                new MenuItem("Go To"), new MenuItem("Select All"),
                new MenuItem("Time Stamp") };
        MenuItem[] mi3 = { new MenuItem("Choose Font"), new MenuItem("Compile"),
                new MenuItem("Run") };
        MenuItem[] mi4 = { new MenuItem("Help Topics"),
                new MenuItem("About TextEditor") };
        for (MenuItem menuItem : mi1) {
            m1.add(menuItem);
            menuItem.addActionListener(this);
        }
        for (MenuItem menuItem : mi2) {
            m2.add(menuItem);
            menuItem.addActionListener(this);
        }
        m3.add(checkBox);
        checkBox.addActionListener(this);
        for (MenuItem menuItem : mi3) {
            m3.add(menuItem);
            menuItem.addActionListener(this);
        }
        for (MenuItem menuItem : mi4) {
            m4.add(menuItem);
            menuItem.addActionListener(this);
        }
        MyWindowsAdapter mw = new MyWindowsAdapter(this);
        addWindowListener(mw);
        setSize(500, 500);
        setTitle("untitled notepad");
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String arg = ae.getActionCommand();
        if (arg.equals("New")) {
            dispose();
            TextEditor t11 = new TextEditor();
            t11.setSize(500, 500);
            t11.setVisible(true);
        }
        try {
            if (arg.equals("Open")) {
                FileDialog fd1 = new FileDialog(this, "Select File", FileDialog.LOAD);
                fd1.setVisible(true);
                StringBuilder s4 = new StringBuilder();
                s2 = fd1.getFile();
                s3 = fd1.getDirectory();
                s32 = s3 + s2;
                File f = new File(s32);
                FileInputStream fii = new FileInputStream(f);
                len = (int) f.length();
                for (int j = 0; j < len; j++) {
                    char s5 = (char) fii.read();
                    s4.append(s5);
                }
                ta.setText(s4.toString());
                fii.close();
            }
        } catch (IOException ignored) {
        }
        try {
            if (arg.equals("Save As")) {
                FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);
                s7 = dialog1.getDirectory();
                s8 = dialog1.getFile();
                s9 = s7 + s8 + ".txt";
                s6 = ta.getText();
                len1 = s6.length();
                byte[] buf = s6.getBytes();
                File f1 = new File(s9);
                FileOutputStream fOutputStream = new FileOutputStream(f1);
                for (int k = 0; k < len1; k++) {
                    fOutputStream.write(buf[k]);
                }
                fOutputStream.close();
            }
            this.setTitle(s8 + " TextEditor File");
        } catch (IOException ignored) {
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
        }
        if (arg.equals("Select All")) {
            String strText = ta.getText();
            int strLen = strText.length();
            ta.select(0, strLen);
        }
        if (arg.equals("Time Stamp")) {
            String hms = getHMS();
            int loc = ta.getCaretPosition();
            ta.insert(hms, loc);
        }
        if (arg.equals("About TextEditor")) {
            AboutDialog d1 = new AboutDialog(this, "About TextEditor");
            d1.setVisible(true);
            setSize(500, 500);
        }
    }

    private String getHMS() {
        GregorianCalendar gCalendar = new GregorianCalendar();
        String h = String.valueOf(gCalendar.get(Calendar.HOUR));
        String m = String.valueOf(gCalendar.get(Calendar.MINUTE));
        String s = String.valueOf(gCalendar.get(Calendar.SECOND));
        String date = String.valueOf(gCalendar.get(Calendar.DATE));
        String mon = months[gCalendar.get(Calendar.MONTH)];
        String year = String.valueOf(gCalendar.get(Calendar.YEAR));
        return "Time" + " - " + h + ":" + m + ":" + s + " Date" + " - " + date + " " + mon + " " + year + " ";
    }

}



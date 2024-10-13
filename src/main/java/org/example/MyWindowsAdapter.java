package org.example;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyWindowsAdapter extends WindowAdapter {
    TextEditor tt;

    public MyWindowsAdapter(TextEditor ttt) {
        tt = ttt;
    }

    public void windowClosing(WindowEvent we) {
        tt.dispose();
    }
}
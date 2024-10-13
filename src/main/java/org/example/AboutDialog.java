package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutDialog extends Dialog implements ActionListener {
    AboutDialog(Frame parent, String title) {
        super(parent, title, false);
        this.setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(500, 300);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}

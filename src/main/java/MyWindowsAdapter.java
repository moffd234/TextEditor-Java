import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class MyWindowsAdapter extends WindowAdapter {
    TextEditor tt;

    public MyWindowsAdapter(TextEditor ttt) {
        tt = ttt;
    }

    public void windowClosing(WindowEvent we) {
        tt.dispose(); // Deletes all window items and returns the memory to the OS
    }
}
import javax.swing.*;
interface IMidiPlayer {
    void playNote(int note);
    void stopNote(int note);
    void changeInstrument(int instrumentIndex);
    void close();
}

interface IKey {
    int getNote();
    boolean isPressed();
    void setPressed(boolean pressed);
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimplePiano::new);
    }
}
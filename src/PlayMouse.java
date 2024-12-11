import javax.swing.*;
import java.util.Map;

public class PlayMouse  extends JPanel {
    private final Map<Integer, PianoKey> blackKeys;
    private final Map<Integer, PianoKey> whiteKeys;
    private final int[] whiteKeyNotes;
    private final int[] blackKeyNotes;
    private final int KEY_WIDTH = 60;
    private final int KEY_HEIGHT = 200;
    KeyboardPanel keyboardPanel;
    int xRight = 40;
    int yPosition = KEY_HEIGHT - 200;
    int xPosition;
    public PlayMouse(Map<Integer, PianoKey> blackKeys, Map<Integer, PianoKey> whiteKeys, int[] whiteKeyNotes,int[] blackKeyNotes, KeyboardPanel keyboardPanel) {
        this.blackKeys = blackKeys;
        this.whiteKeys = whiteKeys;
        this.whiteKeyNotes = whiteKeyNotes;
        this.blackKeyNotes = blackKeyNotes;
        this.keyboardPanel = keyboardPanel;
    }

    public void handleMousePress(int x, int y) {
        for (int i = 0; i < blackKeyNotes.length; i++) {
            if (blackKeyNotes[i] != 0) {
                int xOffset = (i == 2 || i == 6) ? (int) ((i + 1.75) * KEY_WIDTH + xRight) : (int) ((i + 0.75) * KEY_WIDTH + xRight);
                if (x >= xOffset && x < xOffset + KEY_WIDTH / 2 && y < yPosition + (int) (KEY_HEIGHT * 0.75) && y >= yPosition) {
                    PianoKey blackKey = blackKeys.get(blackKeyNotes[i]);
                    if (blackKey != null) {
                        blackKey.setPressed(true);
                        MidiPlayer.getInstance().playNoteAsync(blackKey.getNote());
                    }
                    keyboardPanel.repaint();
                    return;
                }
            }
        }

        for (int i = 0; i < whiteKeyNotes.length; i++) {
            if (whiteKeyNotes[i] != 0) {
                xPosition = i * KEY_WIDTH + xRight;
                if (x >= xPosition && x < xPosition + KEY_WIDTH && y < yPosition + KEY_HEIGHT && y >= yPosition) {
                    PianoKey pianoKey = whiteKeys.get(whiteKeyNotes[i]);
                    if (pianoKey != null) {
                        pianoKey.setPressed(true);
                        MidiPlayer.getInstance().playNoteAsync(pianoKey.getNote());
                    }
                    keyboardPanel.repaint();
                    return;
                }

            }
        }
    }

    public void handleMouseRelease(int x, int y) {

        for (int i = 0; i < blackKeyNotes.length; i++) {
            if (blackKeyNotes[i] != 0) {
                int xOffset = (i == 2 || i == 6) ? (int) ((i + 1.75) * KEY_WIDTH + xRight) : (int) ((i + 0.75) * KEY_WIDTH + xRight);
                if (x >= xOffset && x < xOffset + KEY_WIDTH / 2 && y < yPosition + (int) (KEY_HEIGHT * 0.75) && y >= yPosition) {
                    PianoKey blackKey = blackKeys.get(blackKeyNotes[i]);
                    if (blackKey != null) {
                        blackKey.setPressed(false);
                        MidiPlayer.getInstance().stopNote(blackKey.getNote());
                    }
                    keyboardPanel.repaint();
                    return;
                }
            }
        }
        for (int i = 0; i < whiteKeyNotes.length; i++) {
            if (whiteKeyNotes[i] != 0) {
                xPosition = i * KEY_WIDTH + xRight;
                if (x >= xPosition && x < xPosition + KEY_WIDTH && y < yPosition + KEY_HEIGHT && y >= yPosition) {
                    PianoKey pianoKey = whiteKeys.get(whiteKeyNotes[i]);
                    if (pianoKey != null) {
                        pianoKey.setPressed(false);
                        MidiPlayer.getInstance().stopNote(pianoKey.getNote());
                    }
                    keyboardPanel.repaint();
                    return;
                }
            }
        }
    }
}

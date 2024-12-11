import javax.swing.*;
import java.util.Map;

public class PlayKey extends JPanel {
    private final Map<Integer, PianoKey> whiteKeys;
    private final Map<Integer, PianoKey> blackKeys;
    private final Map<Integer, Integer> keyCodeToNoteMap;
    GameValidator game;
    private boolean showImage = false;
    KeyboardPanel keyboardPanel;
    ToggleButton toggle;
    public PlayKey(Map<Integer, PianoKey> whiteKeys, Map<Integer, PianoKey> blackKeys, Map<Integer, Integer> keyCodeToNoteMap, GameValidator game, boolean showImage,KeyboardPanel keyboardPanel) {
        this.whiteKeys = whiteKeys;
        this.blackKeys = blackKeys;
        this.keyCodeToNoteMap = keyCodeToNoteMap;
        this.game = game;
        this.showImage = showImage;
        this.keyboardPanel=keyboardPanel;
    }

    public void playKey(int keyCode) {
        Integer note = keyCodeToNoteMap.get(keyCode);
        PianoKey pianoKey = whiteKeys.get(note);
        toggle = new ToggleButton();
        if (pianoKey != null) {
            pianoKey.setPressed(true);
            MidiPlayer.getInstance().playNoteAsync(pianoKey.getNote());
            if (showImage) {
                game.checkKeyPress(note, pianoKey);
            }
            keyboardPanel.repaint();
        }
        PianoKey blackKey = blackKeys.get(note);
        if (blackKey != null) {
            blackKey.setPressed(true);
            MidiPlayer.getInstance().playNoteAsync(blackKey.getNote());
            if (showImage) {
                game.checkKeyPress(note, blackKey);
            }
            keyboardPanel.repaint();
        }
        keyboardPanel.repaint();
        if (showImage && game.getCurrentNoteIndex() >= game.getNotesLength()) {
            showImage = false;
            keyboardPanel.repaint();
            Timer timer = new Timer(500, e -> {
                JOptionPane.showMessageDialog(this, game.getResult());
            });
            timer.setRepeats(false);
            timer.start();
        }

    }

    public void releaseKey(int keyCode) {
        PianoKey pianoKey = whiteKeys.get(keyCodeToNoteMap.get(keyCode));
        if (pianoKey != null) {
            pianoKey.setPressed(false);
            pianoKey.setError(false);
            MidiPlayer.getInstance().stopNote(pianoKey.getNote());
        }

        PianoKey blackKey = blackKeys.get(keyCodeToNoteMap.get(keyCode));
        if (blackKey != null) {
            blackKey.setPressed(false);
            blackKey.setError(false);
            MidiPlayer.getInstance().stopNote(blackKey.getNote());
        }
        keyboardPanel.repaint();
    }
}
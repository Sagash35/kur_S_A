import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class KeyboardPanel extends JPanel {
    private final Map<Integer, PianoKey> whiteKeys;
    private final Map<Integer, PianoKey> blackKeys;
    private final int[] whiteKeyNotes;
    private final int[] blackKeyNotes;
    private final int[] whitekeyBindings;
    private final int[] blackkeyBindings;
    private static final int KEY_WIDTH = 60;
    private static final int KEY_HEIGHT = 200;
    int yPosition = KEY_HEIGHT - 200;
    int xRight = 40;

    public KeyboardPanel(Map<Integer, PianoKey> whiteKeys, Map<Integer, PianoKey> blackKeys, int[] whiteKeyNotes, int[] blackKeyNotes,int[] whitekeyBindings,int[] blackkeyBindings) {
        this.whiteKeys = whiteKeys;
        this.blackKeys = blackKeys;
        this.whiteKeyNotes=whiteKeyNotes;
        this.blackKeyNotes=blackKeyNotes;
        this.whitekeyBindings=whitekeyBindings;
        this.blackkeyBindings=blackkeyBindings;
        setPreferredSize(new Dimension(100, 235));
        setBackground(new Color(0,	49,	83));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawKeys(g);

    }

    private void drawKeys(Graphics g) {
        Font largeFont = new Font("Times new roman", Font.BOLD, 20);
        Font smallFont = new Font("Times new roman", Font.PLAIN, 12);

        for (int i = 0; i < whiteKeyNotes.length; i++) {
            PianoKey key = whiteKeys.get(whiteKeyNotes[i]);
            Color keyColor = key.isPressed() ? Color.GRAY : Color.WHITE;
            if (key.isError()) {
                keyColor = key.isPressed() ? Color.RED : Color.WHITE;
            }

            g.setColor(keyColor);
            int xPosition = i * KEY_WIDTH + xRight;
            g.fillRoundRect(xPosition, yPosition, KEY_WIDTH, KEY_HEIGHT, 10, 10); // Округляем все клавиши
            g.setColor(Color.BLACK);
            g.drawRoundRect(xPosition, yPosition, KEY_WIDTH, KEY_HEIGHT,10,10); // Рисуем границу

            g.setFont(largeFont);
            char keyChar = (char) whitekeyBindings[i];
            g.drawString(String.valueOf(keyChar), xPosition + KEY_WIDTH / 3, KEY_HEIGHT + yPosition - 30);
            g.setFont(smallFont);
            g.drawString(getNoteName(whiteKeyNotes[i]), xPosition + KEY_WIDTH / 3, KEY_HEIGHT + yPosition - 10);
        }

        // Draw black keys
        for (int i = 0; i < blackKeyNotes.length; i++) {
            if (blackKeyNotes[i] != 0) {
                int xOffset;
                PianoKey key = blackKeys.get(blackKeyNotes[i]);
                Color keyColor = key.isPressed() ? Color.GRAY : Color.BLACK;

                if (key.isError()) {
                    keyColor = key.isPressed() ? Color.RED : Color.BLACK;
                }

                xOffset = (i == 2 || i == 6) ? (int) ((i + 1.75) * KEY_WIDTH + xRight) : (int) ((i + 0.75) * KEY_WIDTH + xRight);
                g.setColor(keyColor);
                g.fillRoundRect(xOffset, yPosition, (int) (KEY_WIDTH / 1.75), (int) (KEY_HEIGHT * 0.75), 20, 20); // Округленные черные клавиши
                g.setColor(Color.BLACK);
                g.drawRoundRect(xOffset, yPosition, (int) (KEY_WIDTH / 1.75), (int) (KEY_HEIGHT * 0.75), 20, 20);

                // Draw the label
                g.setColor(Color.WHITE);
                g.setFont(largeFont);
                char keyChar = (char) blackkeyBindings[i];
                g.drawString(String.valueOf(keyChar), xOffset + KEY_WIDTH / 6, (int) (KEY_HEIGHT * 0.75) + yPosition - 25);
                g.setFont(smallFont);
                g.drawString(getNoteName(blackKeyNotes[i]), xOffset + KEY_WIDTH / 12, (int) (KEY_HEIGHT * 0.75) + yPosition - 7);
                g.setColor(new Color(0,	49,	83));
                int xPosition = i * KEY_WIDTH + xRight; // Полное значение, без уменьшения
                g.fillRect(xPosition, yPosition, KEY_WIDTH * 10, KEY_HEIGHT / 25); // Округляем все клавиши
                g.setColor(Color.BLACK);
                g.drawLine(xPosition,yPosition + KEY_HEIGHT - 192,KEY_WIDTH + 580,yPosition + KEY_HEIGHT - 192);
            }
        }
    }



    private String getNoteName(int note) {
        switch (note) {
            case 60: return "до";
            case 61: return "до#";
            case 62: return "ре";
            case 63: return "ре#";
            case 64: return "ми";
            case 65: return "фа";
            case 66: return "фа#";
            case 67: return "соль";
            case 68: return "соль#";
            case 69: return "ля";
            case 70: return "ля#";
            case 71: return "си";
            case 72: return "до";
            case 73: return "до#";
            case 74: return "ре";
            case 75: return "ре#";
            case 76: return "ми";
            default: return "";
        }
    }
}

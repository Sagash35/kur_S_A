import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimplePiano extends JFrame {
    private final Map<Integer, PianoKey> whiteKeys = new HashMap<>();
    private final Map<Integer, PianoKey> blackKeys = new HashMap<>();
    private final Map<Integer, Integer> keyCodeToNoteMap = new HashMap<>();
    private final Map<Integer, Boolean> keyStates = new HashMap<>();
    private Image pianoImage;
    private JLabel imageLabel;
    private String inputFile;
    GameValidator game;
    ToggleButton imageToggleButton;
    PlayKey playKey;
    PlayMouse playMous;
    KeyboardPanel keyboardPanel;
    int musicIndex;
    private final int[] whiteKeyNotes = {60, 62, 64, 65, 67, 69, 71, 72, 74, 76};
    private final int[] blackKeyNotes = {61, 63, 0, 66, 68, 70, 0, 73, 75};

    private final int[] whitekeyBindings = {
            KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
            KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H,
            KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
            KeyEvent.VK_SEMICOLON
    };
    private final int[] blackkeyBindings = {
            KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R,
            KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U,
            KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_P
    };
    private boolean showImage = false;

    public SimplePiano() {
        setTitle("Simple Piano");
        setSize(700, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        loadImage("src/lullaby.png");
        add(imageLabel, BorderLayout.NORTH);

        for (int note : whiteKeyNotes) {
            whiteKeys.put(note, new PianoKey(note));
        }

        for (int note : blackKeyNotes) {
            if (note != 0) {
                blackKeys.put(note, new PianoKey(note));
            }
        }
        keyboardPanel = new KeyboardPanel(whiteKeys, blackKeys, whiteKeyNotes, blackKeyNotes, whitekeyBindings, blackkeyBindings);
        add(keyboardPanel, BorderLayout.SOUTH);
        playKey = new PlayKey(whiteKeys, blackKeys, keyCodeToNoteMap, game, showImage, keyboardPanel);

        playMous = new PlayMouse(blackKeys, whiteKeys, whiteKeyNotes, blackKeyNotes, keyboardPanel);
        imageToggleButton = new ToggleButton();
        initializeControls();
        initializeKeyBindings();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyStates.containsKey(keyCode) && !keyStates.get(keyCode)) {
                    keyStates.put(keyCode, true);
                    playKey.playKey(keyCode);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyStates.containsKey(keyCode) && keyStates.get(keyCode)) {
                    keyStates.put(keyCode, false);
                    playKey.releaseKey(keyCode);
                }
            }
        });

        keyboardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                playMous.handleMousePress(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                playMous.handleMouseRelease(e.getX(), e.getY());
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MidiPlayer.getInstance().close();
            }
        });
        MidiPlayer.getInstance().playNoteAsync(0);
        setVisible(true);
    }

    private void initializeControls() {
        imageToggleButton = new ToggleButton();
        String[] musicNames = {"Колыбельная медведицы", "Гравити Фолс"};
        JComboBox<String> musicComboBox = new JComboBox<>(musicNames);
        musicComboBox.addActionListener(e -> {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            musicIndex = source.getSelectedIndex();
            loadComposition(musicIndex);

            SimplePiano.this.requestFocusInWindow();
        });
        imageToggleButton.addPropertyChangeListener(evt -> {
            showImage = imageToggleButton.isToggled();
            if (showImage) {
                if(musicIndex == 0){
                    inputFile="src/lullaby.txt";
                }
                else if(musicIndex == 1){
                    inputFile="src/gf.txt";
                }
                game = new GameValidator(inputFile);
                playKey = new PlayKey(whiteKeys, blackKeys, keyCodeToNoteMap, game, showImage, keyboardPanel);
            }
            repaint();
        });

        String[] instrumentNames = {"Пианино", "Гитара", "Труба"};
        JComboBox<String> instrumentComboBox = new JComboBox<>(instrumentNames);
        instrumentComboBox.addActionListener(e -> {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            String selectedInstrument = (String) source.getSelectedItem();
            int instrumentIndex = 0;
            if (selectedInstrument.equals("Гитара")) {
                instrumentIndex = 24;
            } else if (selectedInstrument.equals("Труба")) {
                instrumentIndex = 58;
            }
            MidiPlayer.getInstance().changeInstrument(instrumentIndex);
            SimplePiano.this.requestFocusInWindow();
        });

        imageAndControlPanel(instrumentComboBox, musicComboBox);
    }

    private void loadComposition(int musicIndex) {
        // Загрузка информации для "Колыбельная медведицы"
        if (musicIndex == 0) {
            loadImage("src/lullaby.png"); // Load default image
        } else if (musicIndex == 1) {
            loadImage("src/gf.png"); // Load second image
        }
        repaint(); // Repaint to update the displayed image
    }

    private void loadImage(String path) {
        try {
            pianoImage = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(pianoImage);
            imageLabel.setIcon(icon); // Установка иконки в JLabel
            imageLabel.setHorizontalAlignment(JLabel.CENTER); // Выравнивание по центру
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            imageLabel.setBackground(Color.WHITE); // Установка белого фона
            imageLabel.setOpaque(true); // Делаем фон видимым // Set the icon of the JLabel
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void imageAndControlPanel(JComboBox<String> instrumentComboBox, JComboBox<String> musicComboBox) {
        JPanel controlPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(117, 199, 255));
                g.fillRoundRect(50, 10, 580, 45, 20, 20);
                g.setColor(Color.BLACK);
                g.drawRoundRect(50, 10, 580, 45, 20, 20);
            }
        };
        controlPanel.setBackground(new Color(0, 49, 83));
        controlPanel.add(new JLabel("Выбор режима:"));
        controlPanel.add(instrumentComboBox);

        controlPanel.add(new JLabel("Музыка:"));
        controlPanel.add(musicComboBox);

        controlPanel.add(new JLabel("Испытание:"));
        controlPanel.add(imageToggleButton);
        add(controlPanel, BorderLayout.CENTER);
    }

    private void initializeKeyBindings() {
        for (int i = 0; i < whiteKeyNotes.length; i++) {
            keyCodeToNoteMap.put(whitekeyBindings[i], whiteKeyNotes[i]);
            keyStates.put(whitekeyBindings[i], false);
        }

        for (int i = 0; i < blackKeyNotes.length; i++) {
            if (blackKeyNotes[i] != 0) {
                keyCodeToNoteMap.put(blackkeyBindings[i], blackKeyNotes[i]);
                keyStates.put(blackkeyBindings[i], false);
            }
        }
    }
}

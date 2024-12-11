public class MidiPlayer extends AbstractMidiPlayer {
    private static MidiPlayer instance;

    private MidiPlayer() {
        super();
        synthesizer.loadInstrument(instruments[0]);
        channels[0].programChange(0);
    }

    public static MidiPlayer getInstance() {//доступ к экземпляру
        if (instance == null) {
            instance = new MidiPlayer();
        }
        return instance;
    }

    public void playNoteAsync(int note) {
        new Thread(() -> {
            playNote(note);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stopNote(note);
        }).start();
    }

    @Override
    public void playNote(int note) {
        channels[0].noteOn(note, 600);
    }

    @Override
    public void stopNote(int note) {
        channels[0].noteOff(note);
    }

    @Override
    public void changeInstrument(int instrumentIndex) {
        if (instrumentIndex >= 0 && instrumentIndex < instruments.length) {
            synthesizer.loadInstrument(instruments[instrumentIndex]);
            channels[0].programChange(instrumentIndex);
            System.out.println("Инструмент изменен на " + instruments[instrumentIndex].getName());
        }
    }

    @Override
    public void close() {
        try {
            synthesizer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import javax.sound.midi.*;

public abstract class AbstractMidiPlayer implements IMidiPlayer {
    protected Synthesizer synthesizer;
    protected MidiChannel[] channels;
    protected Instrument[] instruments;

    public AbstractMidiPlayer() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            channels = synthesizer.getChannels();
            instruments = synthesizer.getAvailableInstruments();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}

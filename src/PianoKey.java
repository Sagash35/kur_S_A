public class PianoKey extends AbstractPianoKey {
    private boolean isError = false;

    public PianoKey(int note) {
        super(note);
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        this.isError = error;
    }
}

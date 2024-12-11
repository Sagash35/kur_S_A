public abstract class AbstractPianoKey implements IKey {
    protected int note;
    protected boolean isPressed = false;

    public AbstractPianoKey(int note) {
        this.note = note;
    }

    public int getNote() {
        return note;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}

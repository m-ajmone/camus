package camus.core;

public class GcgCell {
    private int state = 0;
    private int newState;

    public GcgCell() {

    }

    public GcgCell(int state) {
        this.state = state;
    }

    public void setNewState(int state) {
        newState = state;
    }

    public void updateState() {
        state = newState;
    }

    public int getState() {
        return state;
    }
}

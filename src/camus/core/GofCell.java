package camus.core;

public class GofCell {
    private boolean state = false;
    private boolean newState;

    public GofCell() {

    }

    public GofCell(boolean state) {
        this.state = state;
    }

    public void setNewState(boolean state) {
        newState = state;
    }

    public void updateState() {
        state = newState;
    }

    public boolean getState() {
        return state;
    }
}

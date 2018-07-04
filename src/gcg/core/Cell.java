package gcg.core;

public class Cell {
    private int state = 0;
    private int newState;

    public Cell() {

    }

    public Cell(int state) {
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

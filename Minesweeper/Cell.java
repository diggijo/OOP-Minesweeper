package Minesweeper;

import javax.swing.*;

public class Cell extends JButton {
    private int bombsAround = 0;
    private boolean isFlagged = false;
    private boolean isBomb = false;

    public Cell (int x, int y) {
        setBombsAround(bombsAround);
        setFlagged(isFlagged);
        setBomb(isBomb);
    }

    public int getBombsAround() {
        return bombsAround;
    }

    public void setBombsAround(int bombsAround) {
        this.bombsAround = bombsAround;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }
}

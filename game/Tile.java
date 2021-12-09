package game;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tile<T extends Thing> {
    private final Lock lock = new ReentrantLock();
    private T thing;
    private int xPos;
    private int yPos;

    public T getThing() {
        return thing;
    }

    public boolean setThing(T thing) {
        if (lock.tryLock()) {
            try {
                this.thing = thing;
                this.thing.setTile(this);
            } finally {
                lock.unlock();
            }
            return true;
        }
        else{
            return false;
        }
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Tile() {
        this.xPos = -1;
        this.yPos = -1;
    }

    public Tile(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

}

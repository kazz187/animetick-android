package net.animetick.animetick_android.component;

/**
 * Created by kazz on 2013/09/27.
 */
public class TransitionData {

    public final static int NULL = 0;

    private int prev;
    private int prevDuration;
    private int next;
    private int nextDuration;

    public TransitionData(int prev, int prevDuration, int next, int nextDuration) {
        this.prev = prev;
        this.prevDuration = prevDuration;
        this.next = next;
        this.nextDuration = nextDuration;
    }

    public int getPrev() {
        return prev;
    }

    public int getPrevDuration() {
        return prevDuration;
    }

    public int getNext() {
        return next;
    }

    public int getNextDuration() {
        return nextDuration;
    }
}

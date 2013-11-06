package net.animetick.animetick_android.model;

import net.animetick.animetick_android.model.ticket.Ticket;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kazz on 2013/11/03.
 */
public class TicketHash {

    private static TicketHash instance = new TicketHash();
    protected ConcurrentMap<ArrayList<Integer>, Ticket> ticketHash
            = new ConcurrentHashMap<ArrayList<Integer>, Ticket>();
    protected AtomicInteger watchedNum = new AtomicInteger(0);

    private TicketHash() {}

    public static TicketHash getInstance() {
        return instance;
    }

    public ConcurrentMap<ArrayList<Integer>, Ticket> getHash() {
        return ticketHash;
    }

    public void ticketWatched(int titleId, int count) {
        if (containsKey(titleId, count)) {
            watchedNum.incrementAndGet();
            setWatched(titleId, count, true);
        }
    }

    public void ticketUnwatched(int titleId, int count) {
        if (containsKey(titleId, count)) {
            watchedNum.decrementAndGet();
            setWatched(titleId, count, false);
        }
    }

    public void resetWatchedNum() {
        watchedNum.set(0);
    }

    public int getWatchedNum() {
        return watchedNum.get();
    }

    public void reset() {
        resetWatchedNum();
        ticketHash.clear();
    }

    private boolean containsKey(int titleId, int count) {
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(titleId);
        key.add(count);
        return ticketHash.containsKey(key);
    }

    private void setWatched(int titleId, int count, boolean watched) {
        ArrayList<Integer> key = new ArrayList<Integer>();
        key.add(titleId);
        key.add(count);
        ticketHash.get(key).setWatched(watched);
    }

}

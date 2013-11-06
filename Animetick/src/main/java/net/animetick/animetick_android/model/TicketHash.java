package net.animetick.animetick_android.model;

import net.animetick.animetick_android.model.ticket.Ticket;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by kazz on 2013/11/03.
 */
public class TicketHash {

    private static TicketHash instance = new TicketHash();
    protected ConcurrentMap<ArrayList<Integer>, Ticket> animeEpisodeHash
            = new ConcurrentHashMap<ArrayList<Integer>, Ticket>();

    private TicketHash() {}

    public static TicketHash getInstance() {
        return instance;
    }

    public ConcurrentMap<ArrayList<Integer>, Ticket> getHash() {
        return animeEpisodeHash;
    }

}

package net.animetick.animetick_android.model.ticket;

import java.util.ArrayList;

/**
 * Created by kazz on 2013/09/20.
 */
public class TicketResult {
    private ArrayList<Ticket> ticketList;
    private boolean isLast;

    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void setIsLast(boolean last) {
        isLast = last;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public ArrayList<Ticket> getTicketList() {
        return ticketList;
    }
}

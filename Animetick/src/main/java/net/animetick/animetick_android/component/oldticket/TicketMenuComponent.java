package net.animetick.animetick_android.component.oldticket;

import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.model.ticket.Ticket;

/**
 * Created by kazz on 2013/09/16.
 */
public abstract class TicketMenuComponent {

    protected Ticket ticket;

    public TicketMenuComponent(Ticket ticket) {
        this.ticket = ticket;
    }

    public abstract void showMenu();

    public abstract void hideMenu();

    public abstract void cancel();

    public Ticket getTicket() {
        return ticket;
    }

    public abstract TextView getWatchButton();

    public abstract ImageView getTweetButton();

}

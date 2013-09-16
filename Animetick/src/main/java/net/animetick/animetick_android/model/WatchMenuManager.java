package net.animetick.animetick_android.model;

import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.AbstractMenuComponent;
import net.animetick.animetick_android.component.UnwatchMenuComponent;
import net.animetick.animetick_android.component.WatchMenuComponent;

/**
 * Created by kazz on 2013/09/16.
 */
public class WatchMenuManager {

    AbstractMenuComponent component;

    public void initWatchMenuComponent(Ticket ticket, TextView watchButton, ImageView tweetButton) {
        if (ticket.isWatched()) {
            new UnwatchMenuComponent(ticket, watchButton, tweetButton, this, true);
        } else {
            new WatchMenuComponent(ticket, watchButton, tweetButton, this, true);
        }
    }

    public void setComponent(AbstractMenuComponent component) {
        this.component = component;
    }

    public void watch(AbstractMenuComponent component) {
        WatchMenuComponent watchMenuComponent = (WatchMenuComponent) component;
        Ticket ticket = watchMenuComponent.getTicket();
        ticket.setWatched(true);
        TextView watchButton = watchMenuComponent.getWatchButton();
        ImageView tweetButton = watchMenuComponent.getTweetButton();
        this.component = new UnwatchMenuComponent(ticket, watchButton, tweetButton, this, false);
    }

    public void unwatch(AbstractMenuComponent component) {
        UnwatchMenuComponent unwatchMenuComponent = (UnwatchMenuComponent) component;
        Ticket ticket = unwatchMenuComponent.getTicket();
        ticket.setWatched(false);
        TextView watchButton = unwatchMenuComponent.getWatchButton();
        ImageView tweetButton = unwatchMenuComponent.getTweetButton();
        this.component = new WatchMenuComponent(ticket, watchButton, tweetButton, this, false);
    }

    public void cancel() {
        if (component != null) {
            component.cancel();
        }
    }
}

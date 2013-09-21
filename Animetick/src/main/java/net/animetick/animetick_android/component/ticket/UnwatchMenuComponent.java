package net.animetick.animetick_android.component.ticket;

import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.model.ticket.Ticket;
import net.animetick.animetick_android.model.WatchMenuManager;

/**
 * Created by kazz on 2013/09/17.
 */
public class UnwatchMenuComponent extends TicketMenuComponent {

    protected AbstractButton watchButton;
    protected ImageView tweetButton;
    protected WatchMenuManager manager;

    public UnwatchMenuComponent(Ticket ticket, TextView watchButton, ImageView tweetButton, WatchMenuManager manager, boolean isInit) {
        super(ticket);
        this.watchButton = new UnwatchButton(watchButton, this);
        this.tweetButton = tweetButton;
        this.manager = manager;
        if (isInit) {
            this.watchButton.init();
        }
    }

    @Override
    public void showMenu() {

    }

    @Override
    public void hideMenu() {

    }

    @Override
    public void cancel() {
        watchButton.cancel();
        watchButton = new UnwatchButton((TextView) watchButton.getView(), this);
        manager.setComponent(null);
    }

    public void unwatchConfirm() {
        manager.cancel();
        manager.setComponent(this);
        watchButton.press();
        this.watchButton = new UnwatchConfirmButton((TextView) watchButton.getView(), this);
    }

    public void unwatch() {
        watchButton.press();
        manager.unwatch(this);
    }

    public TextView getWatchButton() {
        return (TextView) watchButton.getView();
    }

    public ImageView getTweetButton() {
        return tweetButton;
    }

}

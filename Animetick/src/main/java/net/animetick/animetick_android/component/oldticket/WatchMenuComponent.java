package net.animetick.animetick_android.component.oldticket;

import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.model.ticket.Ticket;

/**
 * Created by kazz on 2013/09/16.
 */
public class WatchMenuComponent extends TicketMenuComponent {

    private AbstractButton watchButton;
    private TweetButton tweetButton;
    private WatchMenuManager manager;

    public WatchMenuComponent(Ticket ticket, TextView watchButton, ImageView tweetButton,
                              WatchMenuManager manager, boolean isInit) {
        super(ticket);
        this.watchButton = new WatchButton(watchButton, this);
        this.tweetButton = new TweetButton(tweetButton, this);
        this.manager = manager;
        if (isInit) {
            this.watchButton.init();
        }
    }

    @Override
    public void showMenu() {
        tweetButton.show();
    }

    @Override
    public void hideMenu() {
        tweetButton.hide();
    }

    @Override
    public void cancel() {
        watchButton.cancel();
        watchButton = new WatchButton((TextView) watchButton.getView(), this);
        hideMenu();
        manager.setComponent(null);
    }

    public void watchConfirm() {
        manager.cancel();
        manager.setComponent(this);
        watchButton.press();
        watchButton = new WatchConfirmButton((TextView) watchButton.getView(), this);
        showMenu();
    }

    public void watch(boolean tweet) {
        hideMenu();
        manager.watch(this, tweet, new Runnable() {
            @Override
            public void run() {
                watchButton.press();
            }
        });
    }

    public TextView getWatchButton() {
        return (TextView) watchButton.getView();
    }

    public ImageView getTweetButton() {
        return (ImageView) tweetButton.getView();
    }

}

package net.animetick.animetick_android.component.ticket;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.WatchMenuComponent;
import net.animetick.animetick_android.component.button.TweetButton;
import net.animetick.animetick_android.model.ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/11/02.
 */
public class TicketMenuComponent extends WatchMenuComponent<Ticket> {

    private static final int TWEET = 0;

    private View tweetButtonView;

    public TicketMenuComponent(TextView watchButtonView, List<View> panelViewList, Ticket ticket,
                               float density, MenuManager<Ticket> menuManager) {
        super(menuManager, watchButtonView, panelViewList, density, ticket);
        this.tweetButtonView = panelViewList.get(TWEET);
    }

    @Override
    protected void transitionWatchConfirmMenuComponent() {
        super.transitionWatchConfirmMenuComponent();
        buttonList.add(new TweetButton(tweetButtonView, this, new WatchEvent(true)));
    }

    public static List<View> createPanelViewList(ImageView tweetButtonView) {
        List<View> panelViewList = new ArrayList<View>(2);
        panelViewList.add(tweetButtonView);
        return panelViewList;
    }

}

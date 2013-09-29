package net.animetick.animetick_android.component.newticket;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.MenuPanel;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.model.episode.AnimeEpisode;

import java.util.ArrayList;

/**
 * Created by kazz on 2013/09/27.
 */
public class WatchMenuManager {

    private TextView watchButtonView;
    private ImageView tweetButtonView;
    private ImageView watchHereButtonView;
    private AnimeEpisode ticket;
    private MenuPanel panel;
    private float density;

    public WatchMenuManager(TextView watchButtonView, ImageView tweetButtonView,
                            ImageView watchHereButtonView, AnimeEpisode ticket, float density) {
        this.watchButtonView = watchButtonView;
        this.tweetButtonView = tweetButtonView;
        this.watchHereButtonView = watchHereButtonView;
        this.ticket = ticket;
        this.density = density;
        initPanel();
    }

    private void initComponent() {
        if (ticket.isWatched()) {
            transitionUnwatchMenuComponent();
        } else {
            transitionWatchMenuComponent();
        }
    }

    private void initPanel() {
        ArrayList<View> buttonViewList = new ArrayList<View>();
        buttonViewList.add(watchHereButtonView);
        buttonViewList.add(tweetButtonView);
        float iconWidth = 40 * density;
        this.panel = new MenuPanel(buttonViewList, iconWidth);
        initComponent();
    }

    private void transitionWatchMenuComponent() {
        new WatchButton(watchButtonView, new OnClickEvent<Void>() {

            @Override
            public boolean isAsync() {
                return false;
            }

            @Override
            public boolean onClick() {
                return true;
            }

            @Override
            public void onSuccess() {
                transitionWatchConfirmMenuComponent();
            }

            @Override
            public void onFailure() {}

        });
    }

    private void transitionWatchConfirmMenuComponent() {
        panel.open();
        class WatchEvent implements OnClickEvent {

            boolean isTweet;

            WatchEvent(boolean isTweet) {
                this.isTweet = isTweet;
            }

            @Override
            public boolean isAsync() {
                return true;
            }

            @Override
            public boolean onClick() {
                // 送信
                return true;
            }

            @Override
            public void onSuccess() {
                panel.close();
                transitionUnwatchMenuComponent();
            }

            @Override
            public void onFailure() {
                panel.close();
                transitionWatchMenuComponent();
            }

        }
        new WatchConfirmButton(watchButtonView, new WatchEvent(false));
        new TweetButton(tweetButtonView, new WatchEvent(true));

    }

    private void transitionUnwatchMenuComponent() {
        new UnwatchButton(watchButtonView, new OnClickEvent() {
            @Override
            public boolean isAsync() {
                return false;
            }

            @Override
            public boolean onClick() {
                return true;
            }

            @Override
            public void onSuccess() {
                transitionUnwatchConfirmMenuComponent();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void transitionUnwatchConfirmMenuComponent() {
        new UnwatchConfirmButton(watchButtonView, new OnClickEvent() {
            @Override
            public boolean isAsync() {
                return true;
            }

            @Override
            public boolean onClick() {
                return true;
            }

            @Override
            public void onSuccess() {
                transitionWatchMenuComponent();
            }

            @Override
            public void onFailure() {
                transitionUnwatchMenuComponent();
            }
        });
    }

}

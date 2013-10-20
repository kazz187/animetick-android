package net.animetick.animetick_android.component.newticket;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.Button;
import net.animetick.animetick_android.component.MenuPanel;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.model.episode.AnimeEpisode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kazz on 2013/09/27.
 */
public class WatchMenuComponent {

    private TextView watchButtonView;
    private ImageView tweetButtonView;
    private ImageView watchHereButtonView;
    private AnimeEpisode ticket;
    private MenuPanel panel;
    private float density;
    private WatchMenuManager menuManager;
    private List<Button> buttonList = new CopyOnWriteArrayList<Button>();
    public AtomicBoolean inAction = new AtomicBoolean(false);

    public WatchMenuComponent(TextView watchButtonView, ImageView tweetButtonView,
                              ImageView watchHereButtonView, AnimeEpisode ticket,
                              float density, WatchMenuManager menuManager) {
        this.watchButtonView = watchButtonView;
        this.tweetButtonView = tweetButtonView;
        this.watchHereButtonView = watchHereButtonView;
        this.ticket = ticket;
        this.density = density;
        this.menuManager = menuManager;
        initPanel();
    }

    public void close() {
        if (!this.inAction.compareAndSet(false, true)) {
            return;
        }
        for (Button button : buttonList) {
            button.close();
        }
        this.inAction.set(false);
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

    public void setComponent() {
        this.menuManager.setComponent(this);
    }

    private void transitionWatchMenuComponent() {
        buttonList.clear();
        buttonList.add(new WatchButton(watchButtonView, this, new OnClickEvent() {

            @Override
            public void onSuccess() {
                transitionWatchConfirmMenuComponent();
            }

        }));
    }

    private void transitionWatchConfirmMenuComponent() {
        buttonList.clear();
        panel.open();
        class WatchEvent extends OnClickEvent {

            protected boolean isTweet;

            WatchEvent(boolean isTweet, boolean isAsync) {
                super(isAsync);
                this.isTweet = isTweet;
            }

            @Override
            public boolean onClick() {
                // 送信
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        buttonList.add(new WatchConfirmButton(watchButtonView, this, new WatchEvent(false, true)));
        buttonList.add(new TweetButton(tweetButtonView, this, new WatchEvent(true, true)));
        buttonList.add(new WatchHereButton(watchHereButtonView, this, new OnClickEvent(true) {

            @Override
            public boolean onClick() {
                // 送信
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

        }));

    }

    private void transitionUnwatchMenuComponent() {
        buttonList.clear();
        buttonList.add(new UnwatchButton(watchButtonView, this, new OnClickEvent() {

            @Override
            public void onSuccess() {
                transitionUnwatchConfirmMenuComponent();
            }

        }));
    }

    private void transitionUnwatchConfirmMenuComponent() {
        buttonList.add(new UnwatchConfirmButton(watchButtonView, this, new OnClickEvent(true) {

            @Override
            public void onSuccess() {
                transitionWatchMenuComponent();
            }

            @Override
            public void onFailure() {
                transitionUnwatchMenuComponent();
            }
        }));
    }

}

package net.animetick.animetick_android.component.newticket;

import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.MenuComponent;
import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.model.episode.AnimeEpisode;

/**
 * Created by kazz on 2013/09/27.
 */
public class WatchMenuComponent extends MenuComponent {

    private TextView watchButtonView;
    private ImageView tweetButtonView;
    private ImageView watchHereButtonView;
    private AnimeEpisode ticket;
    private MenuManager menuManager;

    public WatchMenuComponent(TextView watchButtonView, ImageView tweetButtonView,
                              ImageView watchHereButtonView, AnimeEpisode ticket,
                              float density, MenuManager menuManager) {
        super(density);
        this.watchButtonView = watchButtonView;
        this.tweetButtonView = tweetButtonView;
        this.watchHereButtonView = watchHereButtonView;
        this.ticket = ticket;

        this.menuManager = menuManager;
        buttonViewList.add(watchHereButtonView);
        buttonViewList.add(tweetButtonView);
        initPanel();
    }


    protected void initComponent() {
        if (ticket.isWatched()) {
            transitionUnwatchMenuComponent();
        } else {
            transitionWatchMenuComponent();
        }
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

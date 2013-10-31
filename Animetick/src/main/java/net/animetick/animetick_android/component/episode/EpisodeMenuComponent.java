package net.animetick.animetick_android.component.episode;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.MenuComponent;
import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.component.button.TweetButton;
import net.animetick.animetick_android.component.button.UnwatchButton;
import net.animetick.animetick_android.component.button.UnwatchConfirmButton;
import net.animetick.animetick_android.component.button.WatchButton;
import net.animetick.animetick_android.component.button.WatchConfirmButton;
import net.animetick.animetick_android.component.button.WatchHereButton;
import net.animetick.animetick_android.model.episode.AnimeEpisode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/09/27.
 */
public class EpisodeMenuComponent extends MenuComponent {

    private static final int TWEET = 0;
    private static final int WATCH_HERE = 1;
    private TextView watchButtonView;
    private View tweetButtonView;
    private View watchHereButtonView;
    private AnimeEpisode episode;

    public EpisodeMenuComponent(TextView watchButtonView, List<View> panelViewList,
                                AnimeEpisode episode, float density, MenuManager menuManager) {
        super(menuManager, panelViewList, density);
        this.watchButtonView = watchButtonView;
        watchButtonView.setHeight(0);
        this.tweetButtonView = panelViewList.get(TWEET);
        this.watchHereButtonView = panelViewList.get(WATCH_HERE);
        this.episode = episode;
        initComponent();
    }

    protected void initComponent() {
        if (episode.isWatched()) {
            transitionUnwatchMenuComponent();
        } else {
            transitionWatchMenuComponent();
        }
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

    public static List<View> createPanelViewList(ImageView tweetButtonView, ImageView watchHereButtonView) {
        List<View> panelViewList = new ArrayList<View>(2);
        panelViewList.add(tweetButtonView);
        panelViewList.add(watchHereButtonView);
        return panelViewList;
    }

}

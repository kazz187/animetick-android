package net.animetick.animetick_android.component.episode;

import
        android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.component.WatchMenuComponent;
import net.animetick.animetick_android.component.button.TweetButton;
import net.animetick.animetick_android.component.button.WatchHereButton;
import net.animetick.animetick_android.model.Episode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/09/27.
 */
public class EpisodeMenuComponent extends WatchMenuComponent {

    private static final int TWEET = 0;
    private static final int WATCH_HERE = 1;
    private View tweetButtonView;
    private View watchHereButtonView;

    public EpisodeMenuComponent(TextView watchButtonView, List<View> panelViewList,
                                Episode episode, float density, MenuManager menuManager) {
        super(menuManager, watchButtonView, panelViewList, density, episode);
        this.tweetButtonView = panelViewList.get(TWEET);
        this.watchHereButtonView = panelViewList.get(WATCH_HERE);
    }

    protected void transitionWatchConfirmMenuComponent() {
        super.transitionWatchConfirmMenuComponent();
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
                transitionUnwatchMenuComponent();
            }

            @Override
            public void onFailure() {
                transitionWatchMenuComponent();
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

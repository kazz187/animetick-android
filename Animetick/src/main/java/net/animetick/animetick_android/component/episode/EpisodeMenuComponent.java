package net.animetick.animetick_android.component.episode;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.WatchMenuComponent;
import net.animetick.animetick_android.component.button.TweetButton;
import net.animetick.animetick_android.component.button.WatchHereButton;
import net.animetick.animetick_android.model.Episode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/09/27.
 */
public class EpisodeMenuComponent<T extends Episode> extends WatchMenuComponent<T> {

    private static final int TWEET = 0;
    private static final int WATCH_HERE = 1;
    private View tweetButtonView;
    private View watchHereButtonView;

    public EpisodeMenuComponent(TextView watchButtonView, List<View> panelViewList,
                                T episode, float density, MenuManager<T> menuManager) {
        super(menuManager, watchButtonView, panelViewList, density, episode);
        this.tweetButtonView = panelViewList.get(TWEET);
        this.watchHereButtonView = panelViewList.get(WATCH_HERE);
    }

    @Override
    protected void transitionWatchConfirmMenuComponent() {
        super.transitionWatchConfirmMenuComponent();
        buttonList.add(new TweetButton(tweetButtonView, this, new WatchEvent(true)));
        buttonList.add(new WatchHereButton(watchHereButtonView, this, new WatchHereEvent()));
    }

    public static List<View> createPanelViewList(ImageView tweetButtonView, ImageView watchHereButtonView) {
        List<View> panelViewList = new ArrayList<View>(2);
        panelViewList.add(tweetButtonView);
        panelViewList.add(watchHereButtonView);
        return panelViewList;
    }

}

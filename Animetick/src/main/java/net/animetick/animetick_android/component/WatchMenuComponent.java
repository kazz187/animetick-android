package net.animetick.animetick_android.component;

import android.view.View;
import android.widget.TextView;

import net.animetick.animetick_android.component.button.UnwatchButton;
import net.animetick.animetick_android.component.button.UnwatchConfirmButton;
import net.animetick.animetick_android.component.button.WatchButton;
import net.animetick.animetick_android.component.button.WatchConfirmButton;
import net.animetick.animetick_android.model.Episode;

import java.util.List;

/**
 * Created by kazz on 2013/11/01.
 */
abstract public class WatchMenuComponent extends MenuComponent {

    protected TextView watchButtonView;
    private Episode episode;

    public WatchMenuComponent(MenuManager menuManager, TextView watchButtonView,
                              List<View> panelViewList, float density, Episode episode) {
        super(menuManager, panelViewList, density);
        this.watchButtonView = watchButtonView;
        watchButtonView.setHeight(0);
        this.episode = episode;
        initComponent();
    }

    private void initComponent() {
        if (episode.isWatched()) {
            transitionUnwatchMenuComponent();
        } else {
            transitionWatchMenuComponent();
        }
    }

    protected void transitionWatchMenuComponent() {
        buttonList.clear();
        panel.close();
        buttonList.add(new WatchButton(watchButtonView, this, new OnClickEvent() {

            @Override
            public void onSuccess() {
                transitionWatchConfirmMenuComponent();
            }

        }));
    }

    protected class WatchEvent extends OnClickEvent {

        protected boolean isTweet;

        public WatchEvent(boolean isTweet, boolean isAsync) {
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
            transitionUnwatchMenuComponent();
        }

        @Override
        public void onFailure() {
            transitionWatchMenuComponent();
        }

    }

    protected void transitionWatchConfirmMenuComponent() {
        buttonList.clear();
        panel.open();
        buttonList.add(new WatchConfirmButton(watchButtonView, this, new WatchEvent(false, true)));
    }

    protected void transitionUnwatchMenuComponent() {
        buttonList.clear();
        panel.close();
        buttonList.add(new UnwatchButton(watchButtonView, this, new OnClickEvent() {

            @Override
            public void onSuccess() {
                transitionUnwatchConfirmMenuComponent();
            }

        }));
    }

    protected void transitionUnwatchConfirmMenuComponent() {
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

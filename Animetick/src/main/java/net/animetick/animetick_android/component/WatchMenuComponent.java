package net.animetick.animetick_android.component;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.component.button.UnwatchButton;
import net.animetick.animetick_android.component.button.UnwatchConfirmButton;
import net.animetick.animetick_android.component.button.WatchButton;
import net.animetick.animetick_android.component.button.WatchConfirmButton;
import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Episode;
import net.animetick.animetick_android.model.Networking;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kazz on 2013/11/01.
 */
abstract public class WatchMenuComponent extends MenuComponent {

    protected TextView watchButtonView;
    private Episode episode;
    private static JsonFactory jsonFactory = new JsonFactory();

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

    abstract protected class TicketEvent extends OnClickEvent {

        protected boolean isTweet;
        protected String action;

        public TicketEvent(String action, boolean isTweet, boolean isAsync) {
            super(isAsync);
            this.isTweet = isTweet;
            this.action = action;
        }

        @Override
        public boolean onClick() {
            Networking networking = menuManager.createNetworking();
            String url = getRequestUrl();
            Map<String, String> rawParams = new HashMap<String, String>();
            if (isTweet) {
                rawParams.put("twitter", "true");
            }
            try {
                InputStream is = networking.post(url, rawParams);
                ObjectMapper mapper = new ObjectMapper(jsonFactory);
                JsonNode rootNode = mapper.readTree(is);
                if (!rootNode.has("success")) {
                    Log.e(Config.LOG_LABEL, "Failed to post " + action + ".");
                    throw new IOException("Failed to post " + action + ".");
                }
                return rootNode.get("success").booleanValue();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        private String getRequestUrl() {
            int titleId = episode.getTitleId();
            int count = episode.getCount();
            return "/ticket/" + titleId + "/" + count + "/" + action + ".json";
        }

    }

    protected class WatchEvent extends TicketEvent {

        public WatchEvent(boolean isTweet, boolean isAsync) {
            super("watch", isTweet, isAsync);
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

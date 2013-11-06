package net.animetick.animetick_android.component;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.component.button.UnwatchButton;
import net.animetick.animetick_android.component.button.UnwatchConfirmButton;
import net.animetick.animetick_android.component.button.WatchButton;
import net.animetick.animetick_android.component.button.WatchConfirmButton;
import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Episode;
import net.animetick.animetick_android.model.EpisodeManager;
import net.animetick.animetick_android.model.Networking;
import net.animetick.animetick_android.model.TicketHash;

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
    private static Toast toast = null;

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
            public boolean onClick() {
                if (!episode.isBroadcasted()) {
                    toastText("まだ放送されていない作品です。");
                    return false;
                }
                return true;
            }

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

        public WatchEvent(boolean isTweet) {
            super("watch", isTweet, true);
        }

        @Override
        public void onSuccess() {
            transitionUnwatchMenuComponent();
            episode.setWatched(true);
            TicketHash.getInstance().ticketWatched(episode.getTitleId(), episode.getCount());
            String toastText = episode.getTitle() + " #" + episode.getCount() + " を Watch しました。";
            if (isTweet) {
                toastText += "(Tweet しました。)";
            }
            toastText(toastText);
        }

        @Override
        public void onFailure() {
            transitionWatchMenuComponent();
        }

    }

    protected class UnwatchEvent extends TicketEvent {

        public UnwatchEvent() {
            super("unwatch", false, true);
        }

        @Override
        public void onSuccess() {
            transitionWatchMenuComponent();
            episode.setWatched(false);
            TicketHash.getInstance().ticketUnwatched(episode.getTitleId(), episode.getCount());
            String toastText = episode.getTitle() + " #" + episode.getCount() + " を Unwatch しました。";
            toastText(toastText);
        }

        @Override
        public void onFailure() {
            transitionUnwatchMenuComponent();
        }

    }

    protected class WatchHereEvent extends TicketEvent {

        public WatchHereEvent() {
            super("watch_here", false, true);
        }

        @Override
        public void onSuccess() {
            transitionUnwatchMenuComponent();
            episode.setWatched(true);
            for (int i = 0; i <= episode.getCount(); i++) {
                TicketHash.getInstance().ticketWatched(episode.getTitleId(), i);
            }
            EpisodeManager<Episode> episodeManager = menuManager.getEpisodeManager();
            if (episodeManager != null) {
                List<Episode> list = episodeManager.getTemplateList();
                for (Episode epi : list) {
                    if (epi.getCount() <= episode.getCount()) {
                        epi.setWatched(true);
                    }
                }
                episodeManager.refreshView();
            }
            String toastText = episode.getTitle() + " #" + episode.getCount() + " までまとめて Watch しました。";
            toastText(toastText);
        }

        @Override
        public void onFailure() {
            transitionWatchMenuComponent();
        }

    }

    protected void transitionWatchConfirmMenuComponent() {
        buttonList.clear();
        panel.open();
        buttonList.add(new WatchConfirmButton(watchButtonView, this, new WatchEvent(false)));
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
        buttonList.add(new UnwatchConfirmButton(watchButtonView, this, new UnwatchEvent()));
    }

    protected void toastText(String text) {
        Context context = menuManager.getContext();
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

}

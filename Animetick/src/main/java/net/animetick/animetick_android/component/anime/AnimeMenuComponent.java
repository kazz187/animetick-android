package net.animetick.animetick_android.component.anime;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.component.MenuComponent;
import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.component.button.UnwatchButton;
import net.animetick.animetick_android.component.button.UnwatchConfirmButton;
import net.animetick.animetick_android.component.button.WatchButton;
import net.animetick.animetick_android.component.button.WatchConfirmButton;
import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Anime;
import net.animetick.animetick_android.model.Networking;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kazz on 2013/11/01.
 */
public class AnimeMenuComponent extends MenuComponent<Anime> {

    protected TextView watchButtonView;
    private Anime anime;
    private static JsonFactory jsonFactory = new JsonFactory();
    private static Toast toast = null;

    public AnimeMenuComponent(MenuManager<Anime> menuManager, TextView watchButtonView,
                              List<View> panelViewList, float density, Anime anime) {
        super(menuManager, panelViewList, density);
        this.watchButtonView = watchButtonView;
        watchButtonView.setHeight(0);
        this.anime = anime;
        initComponent();
    }

    private void initComponent() {
        if (anime.isWatching()) {
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
                return true;
            }

            @Override
            public void onSuccess() {
                transitionWatchConfirmMenuComponent();
            }

        }));
    }

    abstract protected class AnimeEvent extends OnClickEvent {

        protected String action;

        public AnimeEvent(String action, boolean isAsync) {
            super(isAsync);
            this.action = action;
        }

        @Override
        public boolean onClick() {
            Networking networking = menuManager.createNetworking();
            String url = getRequestUrl();
            Map<String, String> rawParams = new HashMap<String, String>();
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
            int titleId = anime.getTitleId();
            return "/anime/" + titleId + "/" + action + ".json";
        }

    }

    protected class WatchEvent extends AnimeEvent {

        public WatchEvent() {
            super("watch", true);
        }

        @Override
        public void onSuccess() {
            transitionUnwatchMenuComponent();
            anime.setWatched(true);
//            TicketHash.getInstance().ticketWatched(anime.getTitleId(), anime.getCount());
            String toastText = anime.getTitle() + " を Watch しはじめました。";
            toastText(toastText);
        }

        @Override
        public void onFailure() {
            transitionWatchMenuComponent();
        }

    }

    protected class UnwatchEvent extends AnimeEvent {

        public UnwatchEvent() {
            super("unwatch", true);
        }

        @Override
        public void onSuccess() {
            transitionWatchMenuComponent();
            anime.setWatched(false);
//            TicketHash.getInstance().ticketUnwatched(anime.getTitleId(), anime.getCount());
            String toastText = anime.getTitle() + " を Unwatch しました。";
            toastText(toastText);
        }

        @Override
        public void onFailure() {
            transitionUnwatchMenuComponent();
        }

    }

    protected void transitionWatchConfirmMenuComponent() {
        buttonList.clear();
        panel.open();
        buttonList.add(new WatchConfirmButton(watchButtonView, this, new WatchEvent()));
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

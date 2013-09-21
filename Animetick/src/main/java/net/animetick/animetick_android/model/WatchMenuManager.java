package net.animetick.animetick_android.model;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.component.ticket.TicketMenuComponent;
import net.animetick.animetick_android.component.ticket.UnwatchMenuComponent;
import net.animetick.animetick_android.component.ticket.WatchMenuComponent;
import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.ticket.Ticket;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kazz on 2013/09/16.
 */
public class WatchMenuManager {

    private final Authentication authentication;
    private TicketMenuComponent component;
    private static JsonFactory jsonFactory = new JsonFactory();

    public WatchMenuManager(Authentication authentication) {
        this.authentication = authentication;
    }

    public void initWatchMenuComponent(Ticket ticket, TextView watchButton, ImageView tweetButton) {
        if (ticket.isWatched()) {
            new UnwatchMenuComponent(ticket, watchButton, tweetButton, this, true);
        } else {
            new WatchMenuComponent(ticket, watchButton, tweetButton, this, true);
        }
    }

    public void setComponent(TicketMenuComponent component) {
        this.component = component;
    }

    public TicketMenuComponent getComponent() {
        return this.component;
    }

    public void watch(TicketMenuComponent component, boolean tweet) {
        WatchAsyncTask task = new WatchAsyncTask(component, "watch", this, tweet);
        task.execute();
    }

    public void unwatch(TicketMenuComponent component) {
        WatchAsyncTask task = new WatchAsyncTask(component, "unwatch", this);
        task.execute();
    }

    class WatchAsyncTask extends AsyncTask<Void, Void, Boolean> {

        TicketMenuComponent menuComponent;
        Ticket ticket;
        String action;
        WatchMenuManager manager;
        boolean tweet = false;

        public WatchAsyncTask(TicketMenuComponent menuComponent, String action, WatchMenuManager manager, boolean tweet) {
            this(menuComponent, action, manager);
            this.tweet = tweet;
        }

        public WatchAsyncTask(TicketMenuComponent menuComponent, String action, WatchMenuManager manager) {
            super();
            this.menuComponent = menuComponent;
            this.ticket = menuComponent.getTicket();
            this.action = action;
            this.manager = manager;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Networking networking = new Networking(authentication);
            int titleId = ticket.getTitleId();
            int count = ticket.getCount();
            String path = "/ticket/" + titleId + "/" + count + "/" + action + ".json";
            Map<String, String> rawParams = new HashMap<String, String>();
            if (tweet) {
                rawParams.put("twitter", "true");
            }
            try {
                InputStream is = networking.post(path, rawParams);
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

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (!isSuccess) {
                component.cancel();
                return;
            }
            TextView watchButton = component.getWatchButton();
            ImageView tweetButton = component.getTweetButton();
            if ("watch".equals(action)) {
                ticket.setWatched(true);
                component = new UnwatchMenuComponent(ticket, watchButton, tweetButton, manager, false);
            } else if ("unwatch".equals(action)) {
                ticket.setWatched(false);
                component = new WatchMenuComponent(ticket, watchButton, tweetButton, manager, false);
            }
        }

    }

    public void cancel() {
        if (component != null) {
            component.cancel();
        }
    }

}

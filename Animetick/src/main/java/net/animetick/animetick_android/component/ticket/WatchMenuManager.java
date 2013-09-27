package net.animetick.animetick_android.component.ticket;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Networking;
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
    private Context context;
    private int watchedNum = 0;

    public WatchMenuManager(Authentication authentication, Context context) {
        this.authentication = authentication;
        this.context = context;
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

    public void watch(TicketMenuComponent component, boolean tweet, Runnable callback) {
        WatchAsyncTask task = new WatchAsyncTask(component, "watch", this, callback, tweet);
        task.execute();
    }

    public void unwatch(TicketMenuComponent component, Runnable callback) {
        WatchAsyncTask task = new WatchAsyncTask(component, "unwatch", this, callback);
        task.execute();
    }

    class WatchAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private final Runnable callback;
        TicketMenuComponent menuComponent;
        Ticket ticket;
        String action;
        WatchMenuManager manager;
        boolean tweet = false;

        public WatchAsyncTask(TicketMenuComponent menuComponent, String action,
                              WatchMenuManager manager, Runnable callback, boolean tweet) {
            this(menuComponent, action, manager, callback);
            this.tweet = tweet;
        }

        public WatchAsyncTask(TicketMenuComponent menuComponent, String action,
                              WatchMenuManager manager, Runnable callback) {
            super();
            this.menuComponent = menuComponent;
            this.ticket = menuComponent.getTicket();
            this.action = action;
            this.manager = manager;
            this.callback = callback;
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
                incrementWatchedNum();
            } else if ("unwatch".equals(action)) {
                ticket.setWatched(false);
                component = new WatchMenuComponent(ticket, watchButton, tweetButton, manager, false);
                decrementWatchedNum();
            }
            callback.run();
            String toastText = "";
            if (action.equals("watch")) {
                toastText = ticket.getTitle() + " #" + ticket.getCount() + " を Watch しました。";
                if (tweet) {
                    toastText += "(Tweet しました。)";
                }
            } else if (action.equals("unwatch")) {
                toastText = ticket.getTitle() + " #" + ticket.getCount() + " を Unwatch しました。";
            }
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        }

    }

    public void incrementWatchedNum() {
        watchedNum++;
    }

    public void decrementWatchedNum() {
        watchedNum--;
    }

    public int getWatchedNum() {
        return watchedNum;
    }

    public void resetWatchedNum() {
        watchedNum = 0;
    }

    public void cancel() {
        if (component != null) {
            component.cancel();
        }
    }

}

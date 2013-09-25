package net.animetick.animetick_android.model.ticket;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Networking;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kazz on 2013/09/13.
 */
public class TicketManager {

    private int page = 0;
    private boolean isLast = false;
    private Authentication authentication;
    private TicketAdapter adapter;
    private AtomicBoolean running = new AtomicBoolean(false);
    private ConcurrentHashMap<ArrayList<Integer>, Ticket> ticketHash = new ConcurrentHashMap<ArrayList<Integer>, Ticket>();

    public TicketManager(TicketAdapter adapter, Authentication authentication) {
        this.authentication = authentication;
        this.adapter = adapter;
    }

    public void loadTickets(boolean reset, final ListView listView, final View footerView, final Runnable postTask) {
        if (running.getAndSet(true)) {
            return;
        }
        if (reset) {
            page = 0;
            ticketHash.clear();
            isLast = false;
            adapter.getWatchMenuManager().resetWatchedNum();
        }
        if (isLast) {
            running.set(false);
            return;
        }
        if (listView.getFooterViewsCount() < 1) {
            listView.addFooterView(footerView);
        }
        final AsyncTask<Void, Void, List<Ticket>> task = new AsyncTask<Void, Void, List<Ticket>>() {

            @Override
            protected List<Ticket> doInBackground(Void... params) {
                Networking networking = new Networking(authentication);
                String path = getRequestPath();
                InputStream is;
                try {
                    is = networking.get(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Config.LOG_LABEL, "Failed to get ticket list. path: " + path);
                    running.set(false);
                    return null;
                }

                TicketListFactory ticketListFactory = new TicketListFactory();
                ArrayList<Ticket> ticketList;
                try {
                    TicketResult ticketResult = ticketListFactory.createTicketList(is);
                    ticketList = ticketResult.getTicketList();
                    isLast = ticketResult.getIsLast();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Config.LOG_LABEL, "Failed to parse ticket list.");
                    running.set(false);
                    return null;
                }
                return getUniqueTickets(ticketList);
            }

            @Override
            protected void onPostExecute(List<Ticket> result) {
                Log.e(Config.LOG_LABEL, "loaded: " + page);
                if (result != null) {
                    if (page == 0) {
                        adapter.clear();
                    }
                    page++;
                    adapter.addAll(result);
                }
                running.set(false);
                if (isLast) {
                    listView.removeFooterView(footerView);
                }
                if (postTask != null) {
                    postTask.run();
                }
            }

        };
        task.execute();
    }

    private List<Ticket> getUniqueTickets(List<Ticket> ticketList) {
        ArrayList<Ticket> resultTicketList = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            ArrayList<Integer> key = new ArrayList<Integer>();
            key.add(ticket.getTitleId());
            key.add(ticket.getCount());
            Ticket existTicket = ticketHash.putIfAbsent(key, ticket);
            if (existTicket == null) {
                resultTicketList.add(ticket);
            }
        }
        return resultTicketList;
    }

    private String getRequestPath() {
        int watchedNum = adapter.getWatchMenuManager().getWatchedNum();
        return "/ticket/list/" + page + "/" + watchedNum + ".json";
    }

}

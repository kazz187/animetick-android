package net.animetick.animetick_android.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Networking;
import net.animetick.animetick_android.model.Ticket;
import net.animetick.animetick_android.model.TicketAdapter;
import net.animetick.animetick_android.model.TicketListFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/08/10.
 */
public class TicketListFragment extends Fragment {
    private TicketAdapter ticketAdapter = null;
    private Authentication authentication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        authentication = new Authentication(getActivity());
        //View view = super.onCreateView(inflater, container, savedInstanceState);
        PullToRefreshListView listView = new PullToRefreshListView(getActivity());
        ticketAdapter = new TicketAdapter(getActivity());

        listView.setAdapter(ticketAdapter);
        reloadTickets();
        return listView;
    }

    private void reloadTickets() {
        AsyncTask<Void, Void, List<Ticket>> task = new AsyncTask<Void, Void, List<Ticket>>() {
            @Override
            protected List<Ticket> doInBackground(Void... params) {
                Networking networking = new Networking(authentication);
                String path = "/ticket/list/0.json";
                InputStream is;
                try {
                    is = networking.get(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Config.LOG_LABEL, "Failed to get ticket list. path: " + path);
                    return null;
                }

                TicketListFactory ticketListFactory = new TicketListFactory();
                ArrayList<Ticket> ticketList;
                try {
                    ticketList = ticketListFactory.createTicketList(is);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Config.LOG_LABEL, "Failed to parse ticket list.");
                    return null;
                }
                return ticketList;
            }

            @Override
            protected void onPostExecute(List<Ticket> result) {
                if (result != null) {
                    ticketAdapter.clear();
                    for (Ticket ticket : result) {
                        ticketAdapter.add(ticket);
                    }
                    //getListView().setSelection(0);
                }
            }
        };
        task.execute();
    }

}
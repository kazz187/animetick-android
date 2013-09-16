package net.animetick.animetick_android.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.TicketAdapter;
import net.animetick.animetick_android.model.TicketManager;

/**
 * Created by kazz on 2013/08/10.
 */
public class TicketListFragment extends Fragment {
    private Authentication authentication;
    private TicketManager ticketManager;
    private PullToRefreshListView listView;
    private TicketAdapter ticketAdapter;
    private View footer;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        authentication = new Authentication(activity);
        ticketAdapter = new TicketAdapter(activity);
        ticketManager = new TicketManager(ticketAdapter, authentication);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ticketManager.loadTickets(true);
        listView = new PullToRefreshListView(getActivity());
        listView.getRefreshableView().addFooterView(getFooterLayout());
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(final PullToRefreshBase<ListView> refreshView) {
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        ticketManager.loadTickets(true);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        super.onPostExecute(result);
                        refreshView.onRefreshComplete();
                    }
                };
                task.execute();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO: ticketAdapter.getWatchButtonManager().resetWatchMenu("watch");
                ticketAdapter.getWatchMenuManager().cancel();
                if (totalItemCount == firstVisibleItem + visibleItemCount) {
                    ticketManager.loadTickets(false);
                    Log.e(Config.LOG_LABEL, "End!!");
                }
            }

        });
        listView.setAdapter(ticketAdapter);
        return listView;
    }

    private View getFooterLayout() {
        if (footer == null) {
            footer = getActivity().getLayoutInflater().inflate(R.layout.ticket_footer, null);
        }
        return footer;
    }
}
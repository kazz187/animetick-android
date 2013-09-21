package net.animetick.animetick_android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.activity.AnimeEpisodeActivity;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Ticket;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ticketAdapter.getWatchMenuManager().getComponent() != null) {
                    ticketAdapter.getWatchMenuManager().cancel();
                    return;
                }
                Ticket ticket = ticketAdapter.getItem(position - 1);
                moveToAnimeEpisodeActivity();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        ticketAdapter.getWatchMenuManager().cancel();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        ticketAdapter.getWatchMenuManager().cancel();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount == firstVisibleItem + visibleItemCount) {
                    ticketManager.loadTickets(false);
                }
            }
        });
        listView.setAdapter(ticketAdapter);
        return listView;
    }

    private void moveToAnimeEpisodeActivity() {
        Intent intent = new Intent(this.getActivity(), AnimeEpisodeActivity.class);
        startActivity(intent);
        this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
    }


    private View getFooterLayout() {
        if (footer == null) {
            footer = getActivity().getLayoutInflater().inflate(R.layout.ticket_footer, null);
        }
        return footer;
    }
}
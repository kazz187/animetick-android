package net.animetick.animetick_android.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.activity.AnimeEpisodeActivity;
import net.animetick.animetick_android.activity.MainActivity;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.ticket.Ticket;
import net.animetick.animetick_android.model.ticket.TicketAdapter;
import net.animetick.animetick_android.model.ticket.TicketManager;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created by kazz on 2013/08/10.
 */
public class TicketListFragment extends Fragment {
    private Authentication authentication;
    private TicketManager ticketManager;
    private ListView listView;
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
        boolean isLoad = listView == null;
        View view = inflater.inflate(R.layout.ticket_list, null);
        if (view == null) {
            return null;
        }
        listView = (ListView) view.findViewById(R.id.ticket_list);
        listView.addFooterView(getFooterLayout());
        if (isLoad) {
            ticketManager.loadTickets(true, listView, getFooterLayout(), null);
        }
        MainActivity activity = (MainActivity) getActivity();
        final PullToRefreshAttacher attacher = activity.getPullToRefreshAttacher();
        attacher.addRefreshableView(listView, new PullToRefreshAttacher.OnRefreshListener() {
            @Override
            public void onRefreshStarted(View view) {
                ticketManager.loadTickets(true, listView, getFooterLayout(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        attacher.setRefreshComplete();
                    }
                });
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ticketAdapter.getWatchMenuManager().getComponent() != null) {
                    ticketAdapter.getWatchMenuManager().cancel();
                    return;
                }
                Ticket ticket = ticketAdapter.getItem(position);
                moveToAnimeEpisodeActivity(ticket);
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
                if (totalItemCount != 0 && totalItemCount != 1 && totalItemCount == firstVisibleItem + visibleItemCount) {
                    ticketManager.loadTickets(false, listView, getFooterLayout(), null);
                }
            }
        });
        listView.setAdapter(ticketAdapter);

        return view;
    }

    private void moveToAnimeEpisodeActivity(Ticket ticket) {
        Intent intent = new Intent(this.getActivity(), AnimeEpisodeActivity.class);
        intent.putExtra("title_id", ticket.getTitleId());
        intent.putExtra("title", ticket.getTitle());
        intent.putExtra("icon_path", ticket.getIconPath());
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
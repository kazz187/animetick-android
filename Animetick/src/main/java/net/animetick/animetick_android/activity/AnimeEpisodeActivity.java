package net.animetick.animetick_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.episode.AnimeEpisodeAdapter;
import net.animetick.animetick_android.model.episode.AnimeEpisodeManager;
import net.animetick.animetick_android.model.episode.AnimeInfo;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class AnimeEpisodeActivity extends Activity {

    PullToRefreshAttacher attacher;
    AnimeEpisodeAdapter adapter;
    Authentication authentication;
    View footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.ticket_list);
        this.authentication = new Authentication(this);
        View view = getLayoutInflater().inflate(R.layout.ticket_list, null);
        if (view == null) {
            return;
        }
        ListView listView = (ListView) view.findViewById(R.id.ticket_list);
        listView.addFooterView(getFooterLayout());
        AnimeInfo animeInfo = new AnimeInfo(getIntent());
        attacher = PullToRefreshAttacher.get(this);
        attacher.addRefreshableView(listView, new PullToRefreshAttacher.OnRefreshListener() {

            @Override
            public void onRefreshStarted(View view) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                attacher.setRefreshComplete();
            }

        });
        adapter = new AnimeEpisodeAdapter(this);
        AnimeEpisodeManager manager = new AnimeEpisodeManager(adapter, authentication, animeInfo);
        manager.loadTickets(true, listView, getFooterLayout(), null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getMenuManager().close();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        adapter.getMenuManager().close();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        adapter.getMenuManager().close();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO: Load more episodes.
//                if (totalItemCount != 0 && totalItemCount != 1 && totalItemCount == firstVisibleItem + visibleItemCount) {
//
//                }
            }
        });


        listView.setAdapter(adapter);
        setContentView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.anime, menu);
        return true;
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_right);
        super.onPause();
    }

    private View getFooterLayout() {
        if (footer == null) {
            footer = this.getLayoutInflater().inflate(R.layout.ticket_footer, null);
        }
        return footer;
    }

}

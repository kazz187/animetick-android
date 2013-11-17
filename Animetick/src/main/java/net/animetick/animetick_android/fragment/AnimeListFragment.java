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
import net.animetick.animetick_android.model.anime.Anime;
import net.animetick.animetick_android.model.anime.AnimeAdapter;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.anime.AnimeManager;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created by kazz on 2013/08/10.
 */
public class AnimeListFragment extends Fragment {
    private AnimeManager animeManager;
    private ListView listView;
    private AnimeAdapter animeAdapter;
    private View footer;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        animeAdapter = new AnimeAdapter(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Authentication authentication = new Authentication(getActivity());
        boolean isLoad = listView == null;
        View view = inflater.inflate(R.layout.anime_list, null);
        if (view == null) {
            return null;
        }
        listView = (ListView) view.findViewById(R.id.anime_list);
        listView.addFooterView(getFooterLayout());
        animeManager = new AnimeManager(animeAdapter, authentication, listView, getFooterLayout());
        if (isLoad) {
            animeManager.loadTickets(true, null);
        }
        MainActivity activity = (MainActivity) getActivity();
        final PullToRefreshAttacher attacher = activity.getPullToRefreshAttacher();
        attacher.addRefreshableView(listView, new PullToRefreshAttacher.OnRefreshListener() {
            @Override
            public void onRefreshStarted(View view) {
                animeManager.loadTickets(true, new Runnable() {
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
                if (!animeAdapter.getMenuManager().close()) {
                    moveToAnimeEpisodeActivity(animeAdapter.getItem(position));
                }
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        animeAdapter.getMenuManager().close();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        animeAdapter.getMenuManager().close();
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
                    animeManager.loadTickets(false, null);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setAdapter(animeAdapter);
    }

    private void moveToAnimeEpisodeActivity(Anime anime) {
        Intent intent = new Intent(this.getActivity(), AnimeEpisodeActivity.class);
        intent.putExtra("title_id", anime.getTitleId());
        intent.putExtra("title", anime.getTitle());
        intent.putExtra("icon_path", anime.getIconPath());
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
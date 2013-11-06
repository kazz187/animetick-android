package net.animetick.animetick_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Episode;
import net.animetick.animetick_android.model.EpisodeAdapter;
import net.animetick.animetick_android.model.EpisodeManager;
import net.animetick.animetick_android.model.episode.AnimeEpisodeManager;
import net.animetick.animetick_android.model.episode.AnimeInfo;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class AnimeEpisodeActivity extends Activity {

    PullToRefreshAttacher attacher;
    EpisodeAdapter<Episode> adapter;
    Authentication authentication;
    View footer;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        attacher.setRefreshComplete();
                    }

                };
                task.execute();
            }

        });
        adapter = new EpisodeAdapter<Episode>(this);
        EpisodeManager manager = new AnimeEpisodeManager(adapter, authentication, animeInfo);
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
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        setContentView(view);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            private static final int SWIPE_MIN_DISTANCE = 120;
            private static final int SWIPE_MAX_OFF_PATH = 250;
            private static final int SWIPE_THRESHOLD_VELOCITY = 200;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    return false;
                }
                if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    finish();
                    return true;
                }
                return false;
            }

        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.anime, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean res;
        switch (item.getItemId()) {
            case R.id.action_open_uri:
                res = true;
                openUri();
                break;
            default:
                res = super.onOptionsItemSelected(item);
                break;
        }
        return res;
    }

    private void openUri() {
        Uri uri = Uri.parse("http://animetick.net");
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
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

package net.animetick.animetick_android.model;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.animetick.animetick_android.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kazz on 2013/09/13.
 */
abstract public class EpisodeManager<T> {

    protected int page = 0;
    protected boolean isLast = false;
    protected Authentication authentication;
    protected ArrayAdapter<T> adapter;
    protected AtomicBoolean running = new AtomicBoolean(false);
    protected ListView listView;
    protected View footerView;

    public EpisodeManager(ArrayAdapter<T> adapter, Authentication authentication,
                          ListView listView, View footerView) {
        this.authentication = authentication;
        this.adapter = adapter;
        this.listView = listView;
        this.footerView = footerView;
    }

    public void loadTickets(boolean reset, final Runnable postTask) {
        if (running.getAndSet(true)) {
            return;
        }
        if (reset) {
            reset();
        }
        if (isLast) {
            running.set(false);
            return;
        }
        if (listView.getFooterViewsCount() < 1) {
            listView.addFooterView(footerView);
        }
        final AsyncTask<Void, Void, List<T>> task = new AsyncTask<Void, Void, List<T>>() {

            @Override
            protected List<T> doInBackground(Void... params) {
                Networking networking = new Networking(authentication);
                String path = getRequestPath();
                InputStream is;
                try {
                    is = networking.get(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Config.LOG_LABEL, "Failed to get anime episode list. path: " + path);
                    running.set(false);
                    return null;
                }

                ArrayList<T> animeEpisodeList;
                try {
                    EpisodeResult<T> animeEpisodeResult = createAnimeEpisodeList(is);
                    animeEpisodeList = animeEpisodeResult.getAnimeEpisodeList();
                    isLast = animeEpisodeResult.getIsLast();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Config.LOG_LABEL, "Failed to parse anime episode list.");
                    running.set(false);
                    return null;
                }
                return getUniqueEpisodes(animeEpisodeList);
            }

            @Override
            protected void onPostExecute(List<T> result) {
                Log.d(Config.LOG_LABEL, "loaded: " + page);
                if (result != null) {
                    if (page == 0) {
                        adapter.clear();
                    }
                    page++;
                    adapter.addAll(result);
                }
                running.set(false);
                if (isLast && listView.getFooterViewsCount() != 0) {
                    try {
                        listView.removeFooterView(footerView);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
                if (postTask != null) {
                    postTask.run();
                }
            }

        };
        task.execute();
    }

    public void refreshView() {
        listView.setAdapter(adapter);
    }

    public List<T> getTemplateList() {
        return new ArrayList<T>();
    }

    protected void reset() {
        page = 0;
        isLast = false;
    }

    abstract protected List<T> getUniqueEpisodes(List<T> animeEpisodeList);

    abstract protected EpisodeResult<T> createAnimeEpisodeList(InputStream is) throws IOException;

    abstract protected String getRequestPath();

}

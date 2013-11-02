package net.animetick.animetick_android.model;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import net.animetick.animetick_android.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kazz on 2013/09/13.
 */
abstract public class EpisodeManager<T extends Episode> {

    protected int page = 0;
    protected boolean isLast = false;
    protected Authentication authentication;
    protected EpisodeAdapter<T> adapter;
    protected AtomicBoolean running = new AtomicBoolean(false);
    protected ConcurrentMap<ArrayList<Integer>, T> animeEpisodeHash
            = new ConcurrentHashMap<ArrayList<Integer>, T>();

    public EpisodeManager(EpisodeAdapter<T> adapter, Authentication authentication) {
        this.authentication = authentication;
        this.adapter = adapter;
    }

    public void loadTickets(boolean reset, final ListView listView, final View footerView, final Runnable postTask) {
        if (running.getAndSet(true)) {
            return;
        }
        if (reset) {
            page = 0;
            animeEpisodeHash.clear();
            isLast = false;
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
                    Log.e(Config.LOG_LABEL, "get " + path);
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
                return getUniqueAnimeEpisodes(animeEpisodeList);
            }

            @Override
            protected void onPostExecute(List<T> result) {
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

    private List<T> getUniqueAnimeEpisodes(List<T> animeEpisodeList) {
        ArrayList<T> resultAnimeEpisodeList = new ArrayList<T>();
        for (T animeEpisode : animeEpisodeList) {
            ArrayList<Integer> key = new ArrayList<Integer>();
            key.add(animeEpisode.getTitleId());
            key.add(animeEpisode.getCount());

            T existAnimeEpisode = animeEpisodeHash.putIfAbsent(key, animeEpisode);
            if (existAnimeEpisode == null) {
                resultAnimeEpisodeList.add(animeEpisode);
            }
        }
        return resultAnimeEpisodeList;
    }

    abstract protected EpisodeResult<T> createAnimeEpisodeList(InputStream is) throws IOException;

    abstract protected String getRequestPath(); /* {
        // TODO
    }*/

}

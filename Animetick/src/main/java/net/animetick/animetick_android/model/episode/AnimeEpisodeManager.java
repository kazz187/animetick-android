package net.animetick.animetick_android.model.episode;

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
public class AnimeEpisodeManager {

    private int page = 0;
    private boolean isLast = false;
    private Authentication authentication;
    private AnimeEpisodeAdapter adapter;
    private AtomicBoolean running = new AtomicBoolean(false);
    private ConcurrentHashMap<ArrayList<Integer>, AnimeEpisode> animeEpisodeHash = new ConcurrentHashMap<ArrayList<Integer>, AnimeEpisode>();
    private AnimeInfo animeInfo;

    public AnimeEpisodeManager(AnimeEpisodeAdapter adapter, Authentication authentication, AnimeInfo animeInfo) {
        this.authentication = authentication;
        this.adapter = adapter;
        this.animeInfo = animeInfo;
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
        final AsyncTask<Void, Void, List<AnimeEpisode>> task = new AsyncTask<Void, Void, List<AnimeEpisode>>() {

            @Override
            protected List<AnimeEpisode> doInBackground(Void... params) {
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

                AnimeEpisodeListFactory animeEpisodeListFactory = new AnimeEpisodeListFactory();
                ArrayList<AnimeEpisode> animeEpisodeList;
                try {
                    AnimeEpisodeResult animeEpisodeResult = animeEpisodeListFactory.createAnimeEpisodeList(is, animeInfo);
                    animeEpisodeList = animeEpisodeResult.getAnimeEpisodeList();
                    isLast = animeEpisodeResult.getIsLast();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Config.LOG_LABEL, "Failed to parse anime episode list.");
                    running.set(false);
                    return null;
                }
//                return getUniqueAnimeEpisodes(animeEpisodeList);
                return animeEpisodeList;
            }

            @Override
            protected void onPostExecute(List<AnimeEpisode> result) {
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

    private List<AnimeEpisode> getUniqueAnimeEpisodes(List<AnimeEpisode> animeEpisodeList) {
        ArrayList<AnimeEpisode> resultAnimeEpisodeList = new ArrayList<AnimeEpisode>();
        for (AnimeEpisode animeEpisode : animeEpisodeList) {
            ArrayList<Integer> key = new ArrayList<Integer>();
            key.add(animeEpisode.getTitleId());
            key.add(animeEpisode.getCount());

            AnimeEpisode existAnimeEpisode = animeEpisodeHash.putIfAbsent(key, animeEpisode);
            if (existAnimeEpisode == null) {
                resultAnimeEpisodeList.add(animeEpisode);
            }
        }
        return resultAnimeEpisodeList;
    }

    private String getRequestPath() {
        return "/anime/" + animeInfo.getTitleId() + "/episode/list.json";
    }

}

package net.animetick.animetick_android.model.anime;

import android.view.View;
import android.widget.ListView;

import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.EpisodeManager;
import net.animetick.animetick_android.model.EpisodeResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kazz on 2013/11/17.
 */
public class AnimeManager extends EpisodeManager<Anime> {

    public AnimeManager(AnimeAdapter adapter, Authentication authentication,
                        ListView listView, View footerView) {
        super(adapter, authentication, listView, footerView);
    }

    @Override
    protected List<Anime> getUniqueEpisodes(List<Anime> animeEpisodeList) {
        return animeEpisodeList;
    }

    @Override
    protected EpisodeResult<Anime> createAnimeEpisodeList(InputStream is) throws IOException {
        AnimeListFactory animeListFactory = new AnimeListFactory();
        return animeListFactory.createAnimeList(is);
    }

    @Override
    protected String getRequestPath() {
        return "/anime/list.json";
    }
}

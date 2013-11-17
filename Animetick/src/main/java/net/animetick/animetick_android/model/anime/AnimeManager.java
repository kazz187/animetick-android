package net.animetick.animetick_android.model.anime;

import android.view.View;
import android.widget.ListView;

import net.animetick.animetick_android.model.Anime;
import net.animetick.animetick_android.model.AnimeAdapter;
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
        return null;
    }

    @Override
    protected EpisodeResult<Anime> createAnimeEpisodeList(InputStream is) throws IOException {
        return null;
    }

    @Override
    protected String getRequestPath() {
        return null;
    }
}

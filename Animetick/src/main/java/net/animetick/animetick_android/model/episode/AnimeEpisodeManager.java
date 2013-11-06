package net.animetick.animetick_android.model.episode;

import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Episode;
import net.animetick.animetick_android.model.EpisodeAdapter;
import net.animetick.animetick_android.model.EpisodeManager;
import net.animetick.animetick_android.model.EpisodeResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kazz on 2013/11/03.
 */
public class AnimeEpisodeManager extends EpisodeManager<Episode> {

    private AnimeInfo animeInfo;

    public AnimeEpisodeManager(EpisodeAdapter<Episode> adapter, Authentication authentication, AnimeInfo animeInfo) {
        super(adapter, authentication);
        this.animeInfo = animeInfo;
    }

    @Override
    protected EpisodeResult<Episode> createAnimeEpisodeList(InputStream is) throws IOException {
        AnimeEpisodeListFactory animeEpisodeListFactory = new AnimeEpisodeListFactory();
        return animeEpisodeListFactory.createAnimeEpisodeList(is, animeInfo);
    }

    @Override
    protected String getRequestPath() {
        return "/anime/" + animeInfo.getTitleId() + "/episode/list.json";
    }

    @Override
    protected List<Episode> getUniqueEpisodes(List<Episode> episodeList) {
        return episodeList;
    }

    @Override
    public List<Episode> getTemplateList() {
        List<Episode> list = super.getTemplateList();
        for (int i = 0; i < adapter.getCount(); i++) {
            list.add(adapter.getItem(i));
        }
        return list;
    }
}

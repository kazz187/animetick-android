package net.animetick.animetick_android.model.episode;

import java.util.ArrayList;

/**
 * Created by kazz on 2013/09/20.
 */
public class AnimeEpisodeResult {

    private ArrayList<AnimeEpisode> animeEpisodeList;
    private boolean isLast;

    public void setAnimeEpisodeList(ArrayList<AnimeEpisode> animeEpisodeList) {
        this.animeEpisodeList = animeEpisodeList;
    }

    public void setIsLast(boolean last) {
        isLast = last;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public ArrayList<AnimeEpisode> getAnimeEpisodeList() {
        return animeEpisodeList;
    }

}

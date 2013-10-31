package net.animetick.animetick_android.model.episode;

import net.animetick.animetick_android.model.Episode;

import java.util.ArrayList;

/**
 * Created by kazz on 2013/09/20.
 */
public class AnimeEpisodeResult {

    private ArrayList<Episode> animeEpisodeList;
    private boolean isLast;

    public void setAnimeEpisodeList(ArrayList<Episode> animeEpisodeList) {
        this.animeEpisodeList = animeEpisodeList;
    }

    public void setIsLast(boolean last) {
        isLast = last;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public ArrayList<Episode> getAnimeEpisodeList() {
        return animeEpisodeList;
    }

}

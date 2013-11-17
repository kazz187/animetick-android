package net.animetick.animetick_android.model;

import java.util.ArrayList;

/**
 * Created by kazz on 2013/09/20.
 */
public class EpisodeResult<T> {

    private ArrayList<T> animeEpisodeList;
    private boolean isLast;

    public void setAnimeEpisodeList(ArrayList<T> animeEpisodeList) {
        this.animeEpisodeList = animeEpisodeList;
    }

    public void setIsLast(boolean last) {
        isLast = last;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public ArrayList<T> getAnimeEpisodeList() {
        return animeEpisodeList;
    }

}

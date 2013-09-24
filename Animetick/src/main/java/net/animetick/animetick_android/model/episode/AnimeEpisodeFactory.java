package net.animetick.animetick_android.model.episode;

import com.fasterxml.jackson.databind.JsonNode;

import java.text.ParseException;

/**
 * Created by kazz on 2013/08/29.
 */
public class AnimeEpisodeFactory {
    public AnimeEpisode createAnimeEpisode(AnimeInfo animeInfo, JsonNode node)
            throws ParseException, IllegalArgumentException {
        AnimeEpisode animeEpisode = new AnimeEpisode();
        animeEpisode.setTitleId(animeInfo.getTitleId());
        animeEpisode.setTitle(animeInfo.getTitle());
        animeEpisode.setIconPath(animeInfo.getIconPath());
        if (node.has("sub_title")) { // sub_title is optional.
            animeEpisode.setSubTitle(node.get("sub_title").textValue());
        }
        if (node.has("count")) {
            animeEpisode.setCount(node.get("count").intValue());
        } else {
            throw new IllegalArgumentException("AnimeEpisode doesn't have count. AnimeEpisode must have count.");
        }
        return animeEpisode;
    }
}

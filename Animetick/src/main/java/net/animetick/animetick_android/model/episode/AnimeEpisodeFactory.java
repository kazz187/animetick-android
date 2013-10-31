package net.animetick.animetick_android.model.episode;

import com.fasterxml.jackson.databind.JsonNode;

import net.animetick.animetick_android.model.Episode;

import java.text.ParseException;

/**
 * Created by kazz on 2013/08/29.
 */
public class AnimeEpisodeFactory {
    public Episode createAnimeEpisode(AnimeInfo animeInfo, JsonNode node)
            throws ParseException, IllegalArgumentException {
        Episode animeEpisode = new Episode();
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
        if (node.has("watched")) {
            animeEpisode.setWatched(node.get("watched").booleanValue());
        } else {
            throw new IllegalArgumentException("AnimeEpisode doesn't have watched flag. AnimeEpisode must have watched flag.");
        }
        return animeEpisode;
    }
}

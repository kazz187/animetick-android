package net.animetick.animetick_android.model.anime;

import com.fasterxml.jackson.databind.JsonNode;

import java.text.ParseException;

/**
 * Created by kazz on 2013/08/29.
 */
public class AnimeFactory {

    public Anime createAnime(JsonNode node)
            throws ParseException, IllegalArgumentException {
        Anime anime = new Anime();
        anime.setEnable(true);
        if (node.has("title_id")) {
            anime.setTitleId(node.get("title_id").intValue());
        } else {
            throw new IllegalArgumentException("Ticket doesn't have title_id. Ticket must have title_id.");
        }
        if (node.has("title")) {
            anime.setTitle(node.get("title").textValue());
        } else {
            throw new IllegalArgumentException("Ticket doesn't have title. Ticket must have title.");
        }
        if (node.has("icon_path")) {
            anime.setIconPath(node.get("icon_path").textValue());
        } else {
            throw new IllegalArgumentException("Ticket doesn't have icon_path. Ticket must have icon_path.");
        }
        if (node.has("watching")) {
            anime.setWatching(node.get("watching").booleanValue());
        } else {
            throw new IllegalArgumentException("AnimeEpisode doesn't have watched flag. AnimeEpisode must have watched flag.");
        }
        return anime;
    }

    public Anime createWeekAnime(String weekName) {
        Anime anime = new Anime();
        anime.setEnable(false);
        anime.setTitle(weekName);
        return anime;
    }

}

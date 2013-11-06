package net.animetick.animetick_android.model.episode;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.model.Episode;
import net.animetick.animetick_android.model.EpisodeResult;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by kazz on 2013/08/20.
 */
public class AnimeEpisodeListFactory {

    private static JsonFactory jsonFactory = new JsonFactory();

    public EpisodeResult<Episode> createAnimeEpisodeList(InputStream is, AnimeInfo animeInfo) throws IOException {
        ArrayList<Episode> animeEpisodeList = new ArrayList<Episode>();
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        JsonNode rootNode = mapper.readTree(is);
        AnimeEpisodeFactory animeEpisodeFactory = new AnimeEpisodeFactory();
        for (JsonNode animeEpisodeNode : rootNode) {
            try {
                Episode animeEpisode = animeEpisodeFactory.createAnimeEpisode(animeInfo, animeEpisodeNode);
                animeEpisodeList.add(animeEpisode);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        EpisodeResult<Episode> animeEpisodeResult = new EpisodeResult<Episode>();
        animeEpisodeResult.setAnimeEpisodeList(animeEpisodeList);

        animeEpisodeResult.setIsLast(true);
        return animeEpisodeResult;
    }

}

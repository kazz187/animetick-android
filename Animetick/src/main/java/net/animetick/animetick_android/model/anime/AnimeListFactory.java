package net.animetick.animetick_android.model.anime;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.model.EpisodeResult;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kazz on 2013/08/20.
 */
public class AnimeListFactory {

    private static JsonFactory jsonFactory = new JsonFactory();

    public EpisodeResult<Anime> createAnimeList(InputStream is) throws IOException {
        ArrayList<Anime> animeList = new ArrayList<Anime>();
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        JsonNode rootNode = mapper.readTree(is);
        AnimeFactory animeFactory = new AnimeFactory();
        Iterator<Map.Entry<String, JsonNode>> weekHash = rootNode.get("list").fields();
        while (weekHash.hasNext()) {
            Map.Entry<String, JsonNode> weekObj = weekHash.next();
            Anime weekAnime = animeFactory.createWeekAnime(weekObj.getKey());
            animeList.add(weekAnime);
            for (JsonNode animeNode : weekObj.getValue()) {
                try {
                    Anime anime = animeFactory.createAnime(animeNode);
                    animeList.add(anime);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        EpisodeResult<Anime> animeResult = new EpisodeResult<Anime>();
        animeResult.setAnimeEpisodeList(animeList);

        animeResult.setIsLast(true);
        return animeResult;
    }

}

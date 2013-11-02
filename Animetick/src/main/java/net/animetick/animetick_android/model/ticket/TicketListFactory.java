package net.animetick.animetick_android.model.ticket;

import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.animetick.animetick_android.config.Config;
import net.animetick.animetick_android.model.EpisodeResult;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by kazz on 2013/08/20.
 */
public class TicketListFactory {

    private static JsonFactory jsonFactory = new JsonFactory();

    public EpisodeResult<Ticket> createTicketList(InputStream is) throws IOException {
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        JsonNode rootNode = mapper.readTree(is);
        if (!rootNode.has("list") || !rootNode.has("last_flag")) {
            Log.e(Config.LOG_LABEL, "Failed to get valid ticket list from Animetick server.");
            throw new IOException("Failed to get valid ticket list from Animetick server.");
        }
        JsonNode listNode = rootNode.get("list");
        TicketFactory ticketFactory = new TicketFactory();
        for (JsonNode ticketNode : listNode) {
            try {
                Ticket ticket = ticketFactory.createTicket(ticketNode);
                ticketList.add(ticket);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        boolean isLast = rootNode.get("last_flag").asBoolean();
        EpisodeResult<Ticket> ticketResult = new EpisodeResult<Ticket>();
        ticketResult.setAnimeEpisodeList(ticketList);
        ticketResult.setIsLast(isLast);
        return ticketResult;
    }

}

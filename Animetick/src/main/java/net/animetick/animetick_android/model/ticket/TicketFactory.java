package net.animetick.animetick_android.model.ticket;

import com.fasterxml.jackson.databind.JsonNode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/08/29.
 */
public class TicketFactory {

    public Ticket createTicket(JsonNode node) throws ParseException, IllegalArgumentException {
        Ticket ticket = new Ticket();
        if (node.has("title_id")) {
            ticket.setTitleId(node.get("title_id").intValue());
        } else {
            throw new IllegalArgumentException("Ticket doesn't have title_id. Ticket must have title_id.");
        }
        if (node.has("title")) {
            ticket.setTitle(node.get("title").textValue());
        } else {
            throw new IllegalArgumentException("Ticket doesn't have title. Ticket must have title.");
        }
        if (node.has("sub_title")) { // sub_title is optional.
            ticket.setSubTitle(node.get("sub_title").textValue());
        }
        if (node.has("count")) {
            ticket.setCount(node.get("count").intValue());
        } else {
            throw new IllegalArgumentException("Ticket doesn't have count. Ticket must have count.");
        }
        if (node.has("icon_path")) {
            ticket.setIconPath(node.get("icon_path").textValue());
        } else {
            throw new IllegalArgumentException("Ticket doesn't have icon_path. Ticket must have icon_path.");
        }
        if (node.has("start_at")) { // start_at is optional.
            ticket.setStartAt(node.get("start_at").textValue());
        }
        if (node.has("end_at")) { // end_at is optional.
            ticket.setEndAt(node.get("end_at").textValue());
        }
        if (node.has("ch_name")) { // ch_name is optional.
            ticket.setChName(node.get("ch_name").textValue());
        }
        if (node.has("ch_number")) { // ch_number is optional.
            ticket.setChNum(node.get("ch_number").intValue());
        }
        if (node.has("flags")) {
            JsonNode flagsNode = node.get("flags");
            List<String> flags = new ArrayList<String>();
            for (JsonNode flagNode : flagsNode) {
                flags.add(flagNode.asText());
            }
            ticket.setFlags(flags);
        }
        return ticket;
    }

}

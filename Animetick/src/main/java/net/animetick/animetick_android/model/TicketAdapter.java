package net.animetick.animetick_android.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kazz on 2013/08/11.
 */
public class TicketAdapter extends ArrayAdapter<Ticket> {

    private LayoutInflater ticketInflater;
    private Authentication authentication;
    private WatchMenuManager watchMenuManager;

    public TicketAdapter(Context context) {
        super(context, R.layout.ticket_list);
        ticketInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.authentication = new Authentication(context);
        this.watchMenuManager = new WatchMenuManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ticketInflater.inflate(R.layout.ticket, null);
            if (convertView == null) {
                return convertView;
            }
        }
        Ticket ticket = getItem(position);
        setTitle(convertView, ticket);
        setSubTitle(convertView, ticket);
        setChannel(convertView, ticket);
        setStartAt(convertView, ticket);
        setIcon(convertView, ticket);
        setWatchButton(convertView, ticket);

        return convertView;
    }

    private void setTitle(View convertView, Ticket ticket) {
        TextView title = (TextView) convertView.findViewById(R.id.ticket_title);
        String ticketTitle = ticket.getTitle();
        if (ticketTitle != null) {
            title.setText(ticketTitle);
        } else {
            title.setText("");
        }
    }

    private void setSubTitle(View convertView, Ticket ticket) {
        TextView subTitle = (TextView) convertView.findViewById(R.id.ticket_sub_title);
        String ticketSubTitle = ticket.getSubTitle();
        int count = ticket.getCount();
        if (ticketSubTitle != null) {
            ticketSubTitle = "#" + count + " " + ticketSubTitle;
            subTitle.setText(ticketSubTitle);
        } else {
            subTitle.setText("#" + count);
        }
    }

    private void setChannel(View convertView, Ticket ticket) {
        TextView channel = (TextView) convertView.findViewById(R.id.ticket_channel);
        String ticketChannel = ticket.getChName();
        if (ticketChannel != null) {
            int ticketChannelNum = ticket.getChNum();
            if (ticketChannelNum > 0) {
                ticketChannel += " / " + String.valueOf(ticketChannelNum) + "ch";
            }
            channel.setText(ticketChannel);
        }
    }

    private void setStartAt(View convertView, Ticket ticket) {
        TextView startAt = (TextView) convertView.findViewById(R.id.ticket_start_at);
        Date ticketStartAt = ticket.getStartAt();
        if (ticketStartAt != null) {
            DateFormat df = new SimpleDateFormat("MM/dd HH:mm ~");
            startAt.setText(df.format(ticketStartAt));
        }
    }

    private void setIcon(View convertView, Ticket ticket) {
        ImageView icon = (ImageView) convertView.findViewById(R.id.ticket_icon);
        icon.setImageDrawable(null);
        Networking networking = new Networking(authentication);
        IconManager.applyIcon(ticket.getIconPath(), networking, icon);
    }

    private void setWatchButton(View convertView, final Ticket ticket) {
        final TextView watchButton = (TextView) convertView.findViewById(R.id.ticket_watch_button);
        final ImageView tweetButton = (ImageView) convertView.findViewById(R.id.ticket_tweet_button);
        watchButton.setHeight(0);
        watchMenuManager.initWatchMenuComponent(ticket, watchButton, tweetButton);
        //watchButtonManager.resetWatchMenu(watchButton, tweetButton, "watch");
        //watchButtonManager.setWatchButton(watchButton, tweetButton);
    }

    public WatchMenuManager getWatchMenuManager() {
        return watchMenuManager;
    }

}

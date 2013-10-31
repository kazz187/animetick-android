package net.animetick.animetick_android.model.ticket;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.IconManager;
import net.animetick.animetick_android.model.Networking;
import net.animetick.animetick_android.component.oldticket.WatchMenuManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        this.watchMenuManager = new WatchMenuManager(authentication, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ticketInflater.inflate(R.layout.ticket, null);
            if (convertView == null) {
                return null;
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
        } else {
            ticketSubTitle = "#" + count;
        }
        List<String> flags = ticket.getFlags();
        for (String flag : flags) {
            ticketSubTitle += " [" + flag + "]";
        }
        subTitle.setText(ticketSubTitle);
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
            String startAtStr = df.format(ticketStartAt);
            String relativeDay = getRelativeDay(ticketStartAt);
            if (relativeDay != null) {
                startAtStr = relativeDay + " " + startAtStr;
            }
            startAt.setText(startAtStr);
        }
    }

    public String getRelativeDay(Date startAt) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar today = shiftDate(now);
        Calendar yesterday = (Calendar) today.clone();
        yesterday.add(Calendar.DATE, -1);
        Calendar tomorrow = (Calendar) today.clone();
        tomorrow.add(Calendar.DATE, 1);
        Calendar startAtCal = Calendar.getInstance();
        startAtCal.setTime(startAt);
        startAtCal = shiftDate(startAtCal);
        if (yesterday.equals(startAtCal)) {
            return "昨晩";
        } else if (today.equals(startAtCal)) {
            return "今晩";
        } else if (tomorrow.equals(startAtCal)) {
            return "翌晩";
        }
        return null;
    }

    private Calendar shiftDate(Calendar original) {
        Calendar calendar = (Calendar) original.clone();
        calendar.add(Calendar.HOUR, -5);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
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
    }

    public WatchMenuManager getWatchMenuManager() {
        return watchMenuManager;
    }

}

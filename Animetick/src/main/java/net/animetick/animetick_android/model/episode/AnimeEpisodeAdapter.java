package net.animetick.animetick_android.model.episode;

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
import net.animetick.animetick_android.model.WatchMenuManager;

/**
 * Created by kazz on 2013/08/11.
 */
public class AnimeEpisodeAdapter extends ArrayAdapter<AnimeEpisode> {

    private LayoutInflater episodeInflater;
    private Authentication authentication;
    private WatchMenuManager watchMenuManager;

    public AnimeEpisodeAdapter(Context context) {
        super(context, R.layout.ticket_list);
        episodeInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.authentication = new Authentication(context);
        this.watchMenuManager = new WatchMenuManager(authentication, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = episodeInflater.inflate(R.layout.ticket, null);
            if (convertView == null) {
                return null;
            }
        }
        AnimeEpisode animeEpisode = getItem(position);
        setTitle(convertView, animeEpisode);
        setSubTitle(convertView, animeEpisode);
        setIcon(convertView, animeEpisode);
        setWatchButton(convertView, animeEpisode);

        return convertView;
    }

    private void setTitle(View convertView, AnimeEpisode animeEpisode) {
        TextView title = (TextView) convertView.findViewById(R.id.ticket_title);
        String animeEpisodeTitle = animeEpisode.getTitle();
        if (animeEpisodeTitle != null) {
            title.setText(animeEpisodeTitle);
        } else {
            title.setText("");
        }
    }

    private void setSubTitle(View convertView, AnimeEpisode animeEpisode) {
        TextView subTitle = (TextView) convertView.findViewById(R.id.ticket_sub_title);
        String animeEpisodeSubTitle = animeEpisode.getSubTitle();
        int count = animeEpisode.getCount();
        if (animeEpisodeSubTitle != null) {
            animeEpisodeSubTitle = "#" + count + " " + animeEpisodeSubTitle;
            subTitle.setText(animeEpisodeSubTitle);
        } else {
            subTitle.setText("#" + count);
        }
    }

    private void setIcon(View convertView, AnimeEpisode animeEpisode) {
        ImageView icon = (ImageView) convertView.findViewById(R.id.ticket_icon);
        icon.setImageDrawable(null);
        Networking networking = new Networking(authentication);
        IconManager.applyIcon(animeEpisode.getIconPath(), networking, icon);
    }

    private void setWatchButton(View convertView, final AnimeEpisode animeEpisode) {
        final TextView watchButton = (TextView) convertView.findViewById(R.id.ticket_watch_button);
        final ImageView tweetButton = (ImageView) convertView.findViewById(R.id.ticket_tweet_button);
        watchButton.setHeight(0);
        //watchMenuManager.initWatchMenuComponent(animeEpisode, watchButton, tweetButton);
    }

    public WatchMenuManager getWatchMenuManager() {
        return watchMenuManager;
    }

}

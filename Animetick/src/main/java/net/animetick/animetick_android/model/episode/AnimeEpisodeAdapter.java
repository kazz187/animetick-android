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
import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.newticket.WatchMenuComponent;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.IconManager;
import net.animetick.animetick_android.model.Networking;

/**
 * Created by kazz on 2013/08/11.
 */
public class AnimeEpisodeAdapter extends ArrayAdapter<AnimeEpisode> {

    private LayoutInflater episodeInflater;
    private Authentication authentication;
    private float density;
    private MenuManager menuManager = new MenuManager();

    public AnimeEpisodeAdapter(Context context) {
        super(context, R.layout.ticket_list);
        episodeInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.authentication = new Authentication(context);
        this.density = context.getResources().getDisplayMetrics().density;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = episodeInflater.inflate(R.layout.episode, null);
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

    private void setWatchButton(View convertView, AnimeEpisode animeEpisode) {
        TextView watchButton = (TextView) convertView.findViewById(R.id.ticket_watch_button);
        ImageView tweetButton = (ImageView) convertView.findViewById(R.id.ticket_tweet_button);
        ImageView watchHereButton = (ImageView) convertView.findViewById(R.id.watch_here);
        watchButton.setHeight(0);
        WatchMenuComponent menuComponent = new WatchMenuComponent(watchButton, tweetButton,
                                                                  watchHereButton, animeEpisode,
                                                                  density, menuManager);
    }

}

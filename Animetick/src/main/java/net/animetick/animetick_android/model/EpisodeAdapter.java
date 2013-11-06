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
import net.animetick.animetick_android.component.MenuManager;
import net.animetick.animetick_android.component.episode.EpisodeMenuComponent;

import java.util.List;

/**
 * Created by kazz on 2013/08/11.
 */
public class EpisodeAdapter<T extends Episode> extends ArrayAdapter<T> {

    protected LayoutInflater episodeInflater;
    protected int resourceId = R.layout.episode;
    protected float density;
    protected MenuManager menuManager;

    public EpisodeAdapter(Context context) {
        super(context, R.layout.ticket_list);
        episodeInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.density = context.getResources().getDisplayMetrics().density;
        Authentication authentication = new Authentication(context);
        this.menuManager = new MenuManager(authentication);
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = episodeInflater.inflate(resourceId, null);
            if (convertView == null) {
                return null;
            }
        }
        T animeEpisode = getItem(position);
        setTitle(convertView, animeEpisode);
        setSubTitle(convertView, animeEpisode);
        setIcon(convertView, animeEpisode);
        setWatchButton(convertView, animeEpisode);
        return convertView;
    }

    protected void setTitle(View convertView, T animeEpisode) {
        TextView title = (TextView) convertView.findViewById(R.id.ticket_title);
        String animeEpisodeTitle = animeEpisode.getTitle();
        if (animeEpisodeTitle != null) {
            title.setText(animeEpisodeTitle);
        } else {
            title.setText("");
        }
    }

    protected void setSubTitle(View convertView, T animeEpisode) {
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

    protected void setIcon(View convertView, T animeEpisode) {
        ImageView icon = (ImageView) convertView.findViewById(R.id.ticket_icon);
        icon.setImageDrawable(null);
        Networking networking = menuManager.createNetworking();
        IconManager.applyIcon(animeEpisode.getIconPath(), networking, icon);
    }

    protected void setWatchButton(View convertView, T animeEpisode) {
        TextView watchButton = (TextView) convertView.findViewById(R.id.ticket_watch_button);
        ImageView tweetButton = (ImageView) convertView.findViewById(R.id.ticket_tweet_button);
        ImageView watchHereButton = (ImageView) convertView.findViewById(R.id.watch_here);
        List<View> panelViewList = EpisodeMenuComponent.createPanelViewList(tweetButton, watchHereButton);
        new EpisodeMenuComponent(watchButton, panelViewList, animeEpisode, density, menuManager);
    }

}

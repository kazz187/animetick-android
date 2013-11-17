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
import net.animetick.animetick_android.component.anime.AnimeMenuComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/08/11.
 */
public class AnimeAdapter extends ArrayAdapter<Anime> {

    protected LayoutInflater animeInflater;
    protected int resourceId = R.layout.episode;
    protected float density;
    protected MenuManager<Anime> menuManager;

    public AnimeAdapter(Context context) {
        super(context, R.layout.anime_list);
        animeInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.density = context.getResources().getDisplayMetrics().density;
        this.menuManager = new MenuManager<Anime>(context);
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = animeInflater.inflate(resourceId, null);
            if (convertView == null) {
                return null;
            }
        }
        Anime anime = getItem(position);
        setTitle(convertView, anime);
        setIcon(convertView, anime);
        setWatchButton(convertView, anime);
        return convertView;
    }

    protected void setTitle(View convertView, Anime anime) {
        TextView title = (TextView) convertView.findViewById(R.id.ticket_title);
        String animeEpisodeTitle = anime.getTitle();
        if (animeEpisodeTitle != null) {
            title.setText(animeEpisodeTitle);
        } else {
            title.setText("");
        }
    }

    protected void setIcon(View convertView, Anime anime) {
        ImageView icon = (ImageView) convertView.findViewById(R.id.ticket_icon);
        icon.setImageDrawable(null);
        Networking networking = menuManager.createNetworking();
        IconManager.applyIcon(anime.getIconPath(), networking, icon);
    }

    protected void setWatchButton(View convertView, Anime anime) {
        TextView watchButton = (TextView) convertView.findViewById(R.id.ticket_watch_button);
        List<View> panelViewList = new ArrayList<View>();
        new AnimeMenuComponent(menuManager, watchButton, panelViewList, density, anime);
    }

}

package net.animetick.animetick_android.model.anime;

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
import net.animetick.animetick_android.model.IconManager;
import net.animetick.animetick_android.model.Networking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/08/11.
 */
public class AnimeAdapter extends ArrayAdapter<Anime> {

    protected LayoutInflater animeInflater;
    protected int resourceId = R.layout.anime;
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
        Anime anime = getItem(position);
        if (!anime.isEnable()) {
            convertView = animeInflater.inflate(R.layout.week_anime, null);
            if (convertView == null) {
                return null;
            }
            setTitle(convertView, anime);
        } else {
            convertView = animeInflater.inflate(resourceId, null);
            if (convertView == null) {
                return null;
            }
            setTitle(convertView, anime);
            setIcon(convertView, anime);
            setWatchButton(convertView, anime);
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position).isEnable();
    }

    protected void setTitle(View convertView, Anime anime) {
        TextView title = (TextView) convertView.findViewById(R.id.anime_title);
        String animeEpisodeTitle = anime.getTitle();
        if (animeEpisodeTitle != null) {
            title.setText(animeEpisodeTitle);
        } else {
            title.setText("");
        }
    }

    protected void setIcon(View convertView, Anime anime) {
        ImageView icon = (ImageView) convertView.findViewById(R.id.anime_icon);
        icon.setImageDrawable(null);
        Networking networking = menuManager.createNetworking();
        IconManager.applyIcon(anime.getIconPath(), networking, icon);
    }

    protected void setWatchButton(View convertView, Anime anime) {
        TextView watchButton = (TextView) convertView.findViewById(R.id.anime_watch_button);
        List<View> panelViewList = new ArrayList<View>();
        new AnimeMenuComponent(menuManager, watchButton, panelViewList, density, anime);
    }

}

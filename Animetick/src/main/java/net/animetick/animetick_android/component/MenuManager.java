package net.animetick.animetick_android.component;

import android.content.Context;

import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Episode;
import net.animetick.animetick_android.model.EpisodeManager;
import net.animetick.animetick_android.model.Networking;

/**
 * Created by kazz on 2013/10/24.
 */
public class MenuManager {

    private MenuComponent component = null;
    protected Authentication authentication;
    protected Context context = null;
    protected EpisodeManager<Episode> episodeManager = null;

    public MenuManager(Context context) {
        this.context = context;
        this.authentication = new Authentication(context);
    }

    public void setComponent(MenuComponent component) {
        if (this.component != component) {
            this.close();
            this.component = component;
        }
    }

    public void setEpisodeManager(EpisodeManager<Episode> episodeManager) {
        this.episodeManager = episodeManager;
    }

    public EpisodeManager<Episode> getEpisodeManager() {
        return episodeManager;
    }

    public boolean close() {
        if (this.component != null) {
            boolean isClosed = this.component.getPanel().getIsOpen();
            this.component.close();
            this.component = null;
            return isClosed;
        }
        return false;
    }

    public Networking createNetworking() {
        return new Networking(authentication);
    }

    public Context getContext() {
        return context;
    }

}

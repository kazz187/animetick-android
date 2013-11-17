package net.animetick.animetick_android.model;

/**
 * Created by kazz on 2013/11/01.
 */
public class Anime {

    protected int titleId;
    protected String title = null;
    protected String iconPath;
    protected boolean isWatching = false;

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getTitleId() {
        return titleId;
    }

    public String getTitle() {
        return title;
    }

    public String getIconPath() {
        return iconPath;
    }

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatched(boolean isWatching) {
        this.isWatching = isWatching;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Anime anime = (Anime) obj;
        return this.titleId == anime.getTitleId();
    }

}

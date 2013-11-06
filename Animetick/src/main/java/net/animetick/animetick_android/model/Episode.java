package net.animetick.animetick_android.model;

/**
 * Created by kazz on 2013/11/01.
 */
public class Episode {

    protected boolean isWatched = false;
    protected int titleId;
    protected String title = null;
    protected int count = -1;
    protected String subTitle = null;
    protected String iconPath;

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
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

    public int getCount() {
        return count;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getIconPath() {
        return iconPath;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean isWatched) {
        this.isWatched = isWatched;
    }

    public boolean isBroadcasted() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Episode episode = (Episode) obj;
        return this.titleId == episode.getTitleId() && this.count == episode.getCount();
    }

}

package net.animetick.animetick_android.model.episode;

/**
 * Created by kazz on 2013/08/11.
 */
public class AnimeEpisode {
    private int titleId;
    private String title = null;
    private int count = -1;
    private String subTitle = null;
    private String iconPath;
    private boolean isWatched = false;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnimeEpisode ticket = (AnimeEpisode) obj;
        return this.titleId == ticket.getTitleId() && this.count == ticket.getCount();
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean isWatched) {
        this.isWatched = isWatched;
    }

}

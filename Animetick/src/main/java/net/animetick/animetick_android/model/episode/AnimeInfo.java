package net.animetick.animetick_android.model.episode;

import android.content.Intent;

/**
 * Created by kazz on 2013/09/24.
 */
public class AnimeInfo {

    private int titleId;
    private String title;
    private String iconPath;

    public AnimeInfo(Intent intent) {
        titleId = intent.getIntExtra("title_id", 0);
        title = intent.getStringExtra("title");
        iconPath = intent.getStringExtra("icon_path");
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

}

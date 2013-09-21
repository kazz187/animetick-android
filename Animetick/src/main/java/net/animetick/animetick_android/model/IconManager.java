package net.animetick.animetick_android.model;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by kazz on 2013/08/29.
 */
public class IconManager {

    private HashMap<String, Drawable> iconHash = new HashMap<String, Drawable>();
    private ConcurrentHashMap<String, List<ImageView>> iconViewHash = new ConcurrentHashMap<String, List<ImageView>>();
    private ConcurrentHashMap<ImageView, String> iconPathHash = new ConcurrentHashMap<ImageView, String>();
    private CopyOnWriteArraySet<String> availableIconSet = new CopyOnWriteArraySet<String>();
    private static IconManager instance = new IconManager();

    public static void applyIcon(final String path, final Networking networking, ImageView iconView) {
        instance.iconPathHash.put(iconView, path);
        List<ImageView> taskList = instance.iconViewHash.get(path);
        if (taskList == null) {
            CopyOnWriteArrayList<ImageView> newTaskList = new CopyOnWriteArrayList<ImageView>();
            taskList = instance.iconViewHash.putIfAbsent(path, newTaskList);
            if (taskList == null) {
                taskList = newTaskList;
            }
        }
        taskList.add(iconView);
        boolean isAvailable = !instance.availableIconSet.add(path);
        if (isAvailable) {
            Drawable drawable = instance.iconHash.get(path);
            if (drawable == null) {
                return;
            }
            taskList.remove(iconView);
            iconView.setImageDrawable(drawable);
            instance.iconPathHash.remove(iconView);
        } else {
            AsyncTask<Void, Void, Drawable> iconRequest = new AsyncTask<Void, Void, Drawable>() {
                @Override
                protected Drawable doInBackground(Void... voids) {
                    try {
                        return Drawable.createFromStream(networking.get(path), path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Drawable drawable) {
                    instance.iconHash.put(path, drawable);
                    List<ImageView> iconViewList = instance.iconViewHash.get(path);
                    List<ImageView> drawnViewList = new ArrayList<ImageView>();
                    for (ImageView iconView : iconViewList) {
                        String expectPath = instance.iconPathHash.get(iconView);
                        if (expectPath != null && expectPath.equals(path)) {
                            iconView.setImageDrawable(drawable);
                            instance.iconPathHash.remove(iconView);
                        }
                        drawnViewList.add(iconView);
                    }
                    iconViewList.removeAll(drawnViewList);
                }
            };
            iconRequest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

}

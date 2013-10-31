package net.animetick.animetick_android.component;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kazz on 2013/09/26.
 */
public class MenuPanel {

    private boolean isOpen = false;
    private List<View> panelLayoutList;
    private List<ObjectAnimator> animList;
    private float iconWidth;

    public MenuPanel(View panelLayout, float iconWidth) {
        this.iconWidth = iconWidth;
        this.panelLayoutList = new ArrayList<View>();
        panelLayoutList.add(panelLayout);
        setupTransition();
    }

    public MenuPanel(List<View> panelLayoutList, float iconWidth) {
        this.iconWidth = iconWidth;
        this.panelLayoutList = panelLayoutList;
        setupTransition();
    }

    private void setupTransition() {
        int width = 0;
        animList = new ArrayList<ObjectAnimator>();
        for (View button : panelLayoutList) {
            width += iconWidth;
            ObjectAnimator anim = ObjectAnimator.ofFloat(button, "translationX", 0, -width);
            anim.setDuration(150);
            anim.setInterpolator(new AccelerateInterpolator(5));
            animList.add(anim);
        }
    }

    public void open() {
        if (isOpen) {
            return;
        }
        for (ObjectAnimator anim : animList) {
            anim.start();
        }
        isOpen = true;
    }

    public void close() {
        if (!isOpen) {
            return;
        }
        for (ObjectAnimator anim : animList) {
            anim.reverse();
        }
        isOpen = false;
    }

}

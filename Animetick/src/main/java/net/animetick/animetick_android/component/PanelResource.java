package net.animetick.animetick_android.component;

import android.view.View;

import java.util.List;

/**
 * Created by kazz on 2013/09/27.
 */
public class PanelResource {


    private View panelLayout;
    private List<ButtonResource> buttonResourceList;

    public PanelResource(View panelLayout, List<ButtonResource> buttonResourceList) {
        this.panelLayout = panelLayout;
        this.buttonResourceList = buttonResourceList;
    }

    public View getPanelLayout() {
        return panelLayout;
    }

    public List<ButtonResource> getButtonResourceList() {
        return buttonResourceList;
    }
}

package net.animetick.animetick_android.component;

import android.view.View;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kazz on 2013/09/26.
 */
abstract public class MenuComponent {

    private static final int WIDTH = 40;
    protected float density;
    protected MenuPanel panel;
    protected List<Button> buttonList = new CopyOnWriteArrayList<Button>();
    public AtomicBoolean inAction = new AtomicBoolean(false);
    protected MenuManager menuManager;

    public MenuComponent(MenuManager menuManager, List<View> panelViewList, float density) {
        this.density = density;
        this.menuManager = menuManager;
        float iconWidth = WIDTH * density;
        this.panel = new MenuPanel(panelViewList, iconWidth);
    }

    public void close() {
        if (!this.inAction.compareAndSet(false, true)) {
            return;
        }
        for (Button button : buttonList) {
            button.close();
        }
        this.inAction.set(false);
    }

    public void setComponent() {
        this.menuManager.setComponent(this);
    }

}

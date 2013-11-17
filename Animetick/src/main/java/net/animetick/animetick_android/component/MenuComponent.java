package net.animetick.animetick_android.component;

import android.view.View;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kazz on 2013/09/26.
 */
abstract public class MenuComponent<T> {

    private static final int WIDTH = 40;
    protected float density;
    protected MenuPanel panel;
    protected List<Button> buttonList = new CopyOnWriteArrayList<Button>();
    public AtomicBoolean inAction = new AtomicBoolean(false);
    protected MenuManager<T> menuManager;

    public MenuComponent(MenuManager<T> menuManager, List<View> panelViewList, float density) {
        this.density = density;
        this.menuManager = menuManager;
        float iconWidth = WIDTH * density;
        this.panel = new MenuPanel(panelViewList, iconWidth);
    }

    public boolean close() {
        if (!this.inAction.compareAndSet(false, true)) {
            return false;
        }
        for (Button button : buttonList) {
            button.close();
        }
        this.inAction.set(false);
        return true;
    }

    public void setComponent() {
        this.menuManager.setComponent(this);
    }

    public MenuPanel getPanel() {
        return panel;
    }

}

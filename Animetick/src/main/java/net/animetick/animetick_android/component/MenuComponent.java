package net.animetick.animetick_android.component;

import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kazz on 2013/09/26.
 */
abstract public class MenuComponent {

    protected ArrayList<View> buttonViewList = new ArrayList<View>();
    protected float density;
    protected MenuPanel panel;
    protected List<Button> buttonList = new CopyOnWriteArrayList<Button>();
    public AtomicBoolean inAction = new AtomicBoolean(false);
    protected MenuManager menuManager;

    public MenuComponent(MenuManager menuManager, float density) {
        this.density = density;
        this.menuManager = menuManager;
    }

    protected void initPanel() {
        float iconWidth = 40 * density;
        this.panel = new MenuPanel(buttonViewList, iconWidth);
        initComponent();
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

    abstract protected void initComponent();

}

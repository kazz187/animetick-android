package net.animetick.animetick_android.component.newticket;

/**
 * Created by kazz on 2013/10/13.
 */
public class WatchMenuManager {

    private WatchMenuComponent component = null;

    public void setComponent(WatchMenuComponent component) {
        if (this.component != component) {
            this.close();
            this.component = component;
        }
    }

    public void close() {
        if (this.component != null) {
            this.component.close();
        }
    }
}

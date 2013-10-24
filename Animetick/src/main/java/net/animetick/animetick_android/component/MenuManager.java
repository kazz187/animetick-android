package net.animetick.animetick_android.component;

/**
 * Created by kazz on 2013/10/24.
 */
public class MenuManager {

    private MenuComponent component = null;

    public void setComponent(MenuComponent component) {
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

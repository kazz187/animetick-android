package net.animetick.animetick_android.component;

import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Networking;

/**
 * Created by kazz on 2013/10/24.
 */
public class MenuManager {

    private MenuComponent component = null;
    protected Authentication authentication;

    public MenuManager(Authentication authentication) {
        this.authentication = authentication;
    }

    public void setComponent(MenuComponent component) {
        if (this.component != component) {
            this.close();
            this.component = component;
        }
    }

    public boolean close() {
        if (this.component != null) {
            boolean isClosed = this.component.getPanel().getIsOpen();
            this.component.close();
            this.component = null;
            return isClosed;
        }
        return false;
    }

    public Networking createNetworking() {
        return new Networking(authentication);
    }

}

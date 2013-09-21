package net.animetick.animetick_android.component.ticket;

import android.view.View;

/**
 * Created by kazz on 2013/09/16.
 */
public abstract class AbstractButton {

    View button;

    public AbstractButton(View button) {
        this.button = button;
    }

    protected abstract void setup();

    public abstract void press();

    public abstract void cancel();

    public abstract void show();

    public abstract void hide();

    public View getView() {
        return button;
    }

    public abstract void init();
}

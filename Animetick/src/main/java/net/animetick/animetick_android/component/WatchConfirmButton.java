package net.animetick.animetick_android.component;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.TextView;

import net.animetick.animetick_android.R;

/**
 * Created by kazz on 2013/09/16.
 */
public class WatchConfirmButton extends AbstractButton {

    private TextView watchButton;
    private WatchMenuComponent menuComponent;

    public WatchConfirmButton(TextView button, WatchMenuComponent menuComponent) {
        super(button);
        this.watchButton = button;
        this.menuComponent = menuComponent;
        setup();
    }

    @Override
    protected void setup() {
        watchButton.setText("Watch?");
        this.watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuComponent.watch(false);
            }
        });
    }

    @Override
    public void press() {
        watchButton.setBackgroundResource(R.drawable.trans_confirm_to_unwatch);
        TransitionDrawable drawable = (TransitionDrawable) watchButton.getBackground();
        if (drawable != null) {
            drawable.startTransition(300);
        }
    }

    @Override
    public void cancel() {
        watchButton.setBackgroundResource(R.drawable.trans_confirm_to_watch);
        TransitionDrawable drawable = (TransitionDrawable) watchButton.getBackground();
        if (drawable != null) {
            drawable.startTransition(300);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}

package net.animetick.animetick_android.component;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.TextView;

import net.animetick.animetick_android.R;

/**
 * Created by kazz on 2013/09/17.
 */
public class UnwatchConfirmButton extends AbstractButton {

    TextView watchButton;
    UnwatchMenuComponent component;

    public UnwatchConfirmButton(TextView watchButton, UnwatchMenuComponent component) {
        super(watchButton);
        this.watchButton = watchButton;
        this.component = component;
        setup();
    }

    @Override
    protected void setup() {
        watchButton.setText("Unwatch?");
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                component.unwatch();
            }
        });
    }

    @Override
    public void press() {
        watchButton.setBackgroundResource(R.drawable.trans_confirm_to_watch);
        TransitionDrawable drawable = (TransitionDrawable) watchButton.getBackground();
        if (drawable != null) {
            drawable.startTransition(300);
        }
    }

    @Override
    public void cancel() {
        watchButton.setBackgroundResource(R.drawable.trans_confirm_to_unwatch);
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

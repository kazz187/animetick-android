package net.animetick.animetick_android.component.ticket;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.TextView;

import net.animetick.animetick_android.R;

/**
 * Created by kazz on 2013/09/17.
 */
public class UnwatchButton extends AbstractButton {

    TextView watchButton;
    UnwatchMenuComponent component;

    public UnwatchButton(TextView watchButton, UnwatchMenuComponent unwatchMenuComponent) {
        super(watchButton);
        this.watchButton = watchButton;
        this.component = unwatchMenuComponent;
        setup();
    }

    @Override
    protected void setup() {
        watchButton.setText("Watched");
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                component.unwatchConfirm();
            }
        });
    }

    @Override
    public void press() {
        watchButton.setBackgroundResource(R.drawable.trans_unwatch_to_confirm);
        TransitionDrawable drawable = (TransitionDrawable) watchButton.getBackground();
        if (drawable != null) {
            drawable.startTransition(300);
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void init() {
        watchButton.setBackgroundResource(R.drawable.trans_unwatch_to_confirm);
    }
}

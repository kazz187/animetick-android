package net.animetick.animetick_android.component.ticket;

import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.model.ticket.Ticket;

/**
 * Created by kazz on 2013/09/16.
 */
public class WatchButton extends AbstractButton {

    TextView watchButton;
    WatchMenuComponent menuComponent;

    public WatchButton(TextView button, WatchMenuComponent menuComponent) {
        super(button);
        this.watchButton = button;
        this.menuComponent = menuComponent;
        setup();
    }

    @Override
    protected void setup() {
        watchButton.setText("Watch");
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ticket ticket = menuComponent.getTicket();
                if (!ticket.isBroadcasted()) {
                    if (v != null) {
                        Toast.makeText(v.getContext(), "まだ放送されていない作品です。",
                                       Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                menuComponent.watchConfirm();
            }
        });
    }

    @Override
    public void press() {
        watchButton.setText("Watch?");
        watchButton.setBackgroundResource(R.drawable.trans_watch_to_confirm);
        TransitionDrawable drawable = (TransitionDrawable) watchButton.getBackground();
        if (drawable != null) {
            drawable.startTransition(300);
        }
    }

    @Override
    public void cancel() {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void init() {
        watchButton.setBackgroundResource(R.drawable.trans_watch_to_confirm);
    }

}

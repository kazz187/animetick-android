package net.animetick.animetick_android.component.button;

import android.widget.TextView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.component.Button;
import net.animetick.animetick_android.component.MenuComponent;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.component.TransitionData;

/**
 * Created by kazz on 2013/09/27.
 */
public class UnwatchButton extends Button {

    public UnwatchButton(String message, TextView view, MenuComponent component, OnClickEvent event) {
        this(view, component, event);
        view.setText(message);
    }
    public UnwatchButton(TextView view, MenuComponent component, OnClickEvent event) {
        super(view, component, event, new TransitionData(TransitionData.NULL, 0,
                R.drawable.trans_unwatch_to_confirm, DURATION_TIME));
        view.setText("Watched");
        view.setBackgroundResource(R.drawable.unwatch_button);
    }

}

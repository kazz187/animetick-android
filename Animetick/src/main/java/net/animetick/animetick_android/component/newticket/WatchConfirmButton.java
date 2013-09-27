package net.animetick.animetick_android.component.newticket;

import android.widget.TextView;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.component.Button;
import net.animetick.animetick_android.component.OnClickEvent;
import net.animetick.animetick_android.component.TransitionData;

/**
 * Created by kazz on 2013/09/27.
 */
public class WatchConfirmButton extends Button {

    public WatchConfirmButton(TextView view, OnClickEvent event) {
        super(view, event, new TransitionData(R.drawable.trans_confirm_to_watch, 150,
                                              R.drawable.trans_confirm_to_unwatch, 150));
        view.setText("Watch");
    }

}

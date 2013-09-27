package net.animetick.animetick_android.component;

import android.view.View;

/**
 * Created by kazz on 2013/09/27.
 */
public class ButtonResource {

    private View view;
    private OnClickEvent clickEvent;
    private TransitionData transitionData;

    public ButtonResource(View view, OnClickEvent clickEvent, TransitionData transitionData) {
        this.view = view;
        this.clickEvent = clickEvent;
        this.transitionData = transitionData;
    }

    public View getView() {
        return view;
    }

    public OnClickEvent getCallback() {
        return clickEvent;
    }

    public TransitionData getTransitionData() {
        return transitionData;
    }

}

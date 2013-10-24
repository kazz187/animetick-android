package net.animetick.animetick_android.component;

import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.view.View;

/**
 * Created by kazz on 2013/09/26.
 */
public class Button {

    protected View view;
    protected MenuComponent component;
    protected OnClickEvent event;
    protected TransitionData transitionData;

    public Button(View view, final MenuComponent component, OnClickEvent event) {
        this(view, component, event, null);
    }

    public Button(View view, final MenuComponent component, final OnClickEvent event,
                  TransitionData transitionData) {
        this.view = view;
        this.component = component;
        this.event = event;
        this.transitionData = transitionData;
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!component.inAction.compareAndSet(false, true)) {
                    return;
                }

                if (!event.isAsync()) {
                    if (event.onClick()) {
                        onSuccess();
                    } else {
                        onFailure();
                    }
                    component.setComponent();
                    component.inAction.set(false);
                    return;
                }
                AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

                    @Override
                    protected Boolean doInBackground(Void... params) {
                        return event.onClick();
                    }

                    @Override
                    protected void onPostExecute(Boolean isSuccess) {
                        if (isSuccess) {
                            onSuccess();
                        } else {
                            onFailure();
                        }
                        component.setComponent();
                        component.inAction.set(false);
                    }

                };
                task.execute();
            }

        });
    }

    protected void onSuccess() {
        event.onSuccess();
        if (transitionData == null)
            return;
        transit(transitionData.getNext(), transitionData.getNextDuration());
    }

    protected void onFailure() {
        event.onFailure();
        if (transitionData == null)
            return;
        transit(transitionData.getPrev(), transitionData.getPrevDuration());
    }

    public void close() {
        this.onFailure();
    }

    private void transit(int transitResource, int duration) {
        if (transitResource == TransitionData.NULL)
            return;
        view.setBackgroundResource(transitResource);
        TransitionDrawable drawable = (TransitionDrawable) view.getBackground();
        if (drawable != null) {
            drawable.startTransition(duration);
        }
    }

}

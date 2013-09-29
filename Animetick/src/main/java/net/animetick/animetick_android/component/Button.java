package net.animetick.animetick_android.component;

import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.view.View;

/**
 * Created by kazz on 2013/09/26.
 */
public class Button {

    protected View view;
    protected OnClickEvent event;
    protected TransitionData transitionData;

    public Button(View view, OnClickEvent event) {
        this(view, event, null);
    }

    public Button(View view, final OnClickEvent event, TransitionData transitionData) {
        this.view = view;
        this.event = event;
        this.transitionData = transitionData;
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!event.isAsync()) {
                    if (event.onClick()) {
                        onSuccess();
                    } else {
                        onFailure();
                    }
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

    private void transit(int transitResource, int duration) {
        if (transitResource == TransitionData.NULL)
            return;
        view.setBackgroundResource(transitResource);
        TransitionDrawable drawable = (TransitionDrawable) view.getBackground();
        if (drawable != null)
            drawable.startTransition(duration);
    }

}

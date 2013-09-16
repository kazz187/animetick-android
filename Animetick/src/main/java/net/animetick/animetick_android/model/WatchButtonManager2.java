package net.animetick.animetick_android.model;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.config.Config;

/**
 * Created by kazz on 2013/09/15.
 */
public class WatchButtonManager2 {
    TextView watchButton = null;
    ImageView tweetButton = null;
    ObjectAnimator transX = null;
    ValueAnimator transColor = null;

    public void resetWatchMenu(String status) {
        resetWatchMenu(this.watchButton, this.tweetButton, status);
    }

    public void resetWatchMenu(TextView watchButton, ImageView tweetButton, String status) {
        if (tweetButton != null) {
            tweetButton.clearAnimation();
        }
        if (transX != null) {
            transX.reverse();
            transX = null;
        }
        if (transColor != null) {
            transColor.reverse();
            transColor = null;
        }
        if (watchButton != null) {
            if (status.equals("watch")) {
                watchButton.setText("Watch");
                setWatchButton(watchButton, tweetButton);
            } else if (status.equals("unwatch")) {
                watchButton.setText("Watched");
                setUnwatchButton(watchButton, tweetButton);
            }
        }
    }

    public void showWatchMenu(final TextView watchButton, final ImageView tweetButton) {
        resetWatchMenu("watch");
        this.watchButton = watchButton;
        this.tweetButton = tweetButton;
        watchButton.setText("Watch?");
        transX = ObjectAnimator.ofFloat(tweetButton, "translationX", 0, -tweetButton.getMeasuredWidth());
        transX.setDuration(150);
        transX.setInterpolator(new AccelerateInterpolator(5));
//        transColor = ObjectAnimator.ofInt(watchButton, "backgroundColor", Color.rgb(0x22, 0x22, 0x22), Color.rgb(0xCC, 0x22, 0x22));
//        transColor.setDuration(400);
//        transColor.setEvaluator(new ArgbEvaluator());
//        transColor.start();
        TransitionDrawable drawable = (TransitionDrawable) watchButton.getBackground();
        drawable.startTransition(400);
        transX.start();
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Config.LOG_LABEL, "watched!");
                resetWatchMenu("unwatch");
            }
        });
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Config.LOG_LABEL, "tweet!");
                resetWatchMenu("unwatch");
            }
        });
    }

    public void setWatchButton(final TextView watchButton, final ImageView tweetButton) {
        if (watchButton == null || tweetButton == null) {
            return;
        }
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWatchMenu(watchButton, tweetButton);
            }
        });
    }

    public void setUnwatchButton(final TextView watchButton, final ImageView tweetButton) {
        if (watchButton == null || tweetButton == null) {
            return;
        }
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnwatchMenu(watchButton);
            }
        });
    }

    public void showUnwatchMenu(final TextView watchButton) {
        resetWatchMenu("watch");
        this.watchButton = watchButton;
        watchButton.setText("Unwatch?");
        transColor = ObjectAnimator.ofInt(watchButton, "backgroundColor", Color.rgb(0x22, 0x22, 0x22), Color.rgb(0xCC, 0x22, 0x22));
        transColor.setDuration(400);
        transColor.setEvaluator(new ArgbEvaluator());
        transColor.start();
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Config.LOG_LABEL, "watched!");
                resetWatchMenu("watch");
            }
        });
    }

}

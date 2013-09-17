package net.animetick.animetick_android.component;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by kazz on 2013/09/16.
 */
public class TweetButton extends AbstractButton {

    ImageView tweetButton;
    private WatchMenuComponent menuComponent;
    private ObjectAnimator transX;

    public TweetButton(ImageView button, WatchMenuComponent menuComponent) {
        super(button);
        this.tweetButton = button;
        this.menuComponent = menuComponent;
        setup();
    }

    @Override
    protected void setup() {
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuComponent.watch(true);
            }
        });
    }

    @Override
    public void press() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void show() {
        transX = ObjectAnimator.ofFloat(tweetButton, "translationX", 0, -tweetButton.getMeasuredWidth());
        transX.setDuration(150);
        transX.setInterpolator(new AccelerateInterpolator(5));
        transX.start();
    }

    @Override
    public void hide() {
        if (transX != null) {
            transX.reverse();
        }
    }

    @Override
    public void init() {

    }
}

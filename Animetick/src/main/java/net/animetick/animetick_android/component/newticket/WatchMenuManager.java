package net.animetick.animetick_android.component.newticket;

import android.widget.ImageView;
import android.widget.TextView;

import net.animetick.animetick_android.component.OnClickEvent;

import net.animetick.animetick_android.model.ticket.Ticket;

/**
 * Created by kazz on 2013/09/27.
 */
public class WatchMenuManager {

    public WatchMenuManager() {

    }

    public void initComponent(TextView watchButton, ImageView tweetButton, Ticket ticket) {
        if (ticket.isWatched()) {
            setupUnwatchMenuComponent(watchButton, tweetButton);
        } else {
            setupWatchMenuComponent(watchButton, tweetButton);
        }
    }

    private void setupWatchMenuComponent(final TextView watchButton, final ImageView tweetButton) {
        new WatchButton(watchButton, new OnClickEvent() {

            @Override
            public boolean onClick() {
                // TODO: 放送済みチェック
                return true;
            }

            @Override
            public void onSuccess() {
                setupWatchConfirmMenuComponent(watchButton, tweetButton);
            }

            @Override
            public void onFailure() {

            }

        });
    }

    private void setupWatchConfirmMenuComponent(final TextView watchButton, final ImageView tweetButton) {
        new WatchConfirmButton(watchButton, new OnClickEvent() {

            @Override
            public boolean onClick() {
                // 送信
                return false;
            }

            @Override
            public void onSuccess() {
                setupUnwatchMenuComponent(watchButton, tweetButton);
            }

            @Override
            public void onFailure() {
                setupWatchConfirmMenuComponent(watchButton, tweetButton);
            }

        });
        new TweetButton(tweetButton, new OnClickEvent() {

            @Override
            public boolean onClick() {
                return false;
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }

        });
    }

    private void setupUnwatchMenuComponent(TextView watchButton, ImageView tweetButton) {
        new UnwatchButton(watchButton, new OnClickEvent() {
            @Override
            public boolean onClick() {
                return false;
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }



}

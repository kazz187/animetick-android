package net.animetick.animetick_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import net.animetick.animetick_android.R;

public class AnimeEpisodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);
        int titleId = getIntent().getIntExtra("title_id", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.anime, menu);
        return true;
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_right);
        super.onPause();
    }

}

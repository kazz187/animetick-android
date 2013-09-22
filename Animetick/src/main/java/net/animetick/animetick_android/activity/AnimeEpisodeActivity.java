package net.animetick.animetick_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import net.animetick.animetick_android.R;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class AnimeEpisodeActivity extends Activity {

    PullToRefreshAttacher attacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.ticket_list);
        View view = getLayoutInflater().inflate(R.layout.ticket_list, null);
        if (view == null) {
            return;
        }
        ListView listView = (ListView) view.findViewById(R.id.ticket_list);
        listView.addFooterView(getLayoutInflater().inflate(R.layout.ticket_footer, null));
        int titleId = getIntent().getIntExtra("title_id", 0);
        attacher = PullToRefreshAttacher.get(this);
        attacher.addRefreshableView(listView, new PullToRefreshAttacher.OnRefreshListener() {

            @Override
            public void onRefreshStarted(View view) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                attacher.setRefreshComplete();
            }

        });

        setContentView(view);
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

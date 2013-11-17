package net.animetick.animetick_android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.animetick.animetick_android.R;
import net.animetick.animetick_android.fragment.AnimeListFragment;
import net.animetick.animetick_android.fragment.TicketListFragment;
import net.animetick.animetick_android.model.Authentication;
import net.animetick.animetick_android.model.Networking;

import java.io.IOException;
import java.util.Locale;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class MainActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private Authentication authentication;
    private PullToRefreshAttacher pullToRefreshAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authentication = new Authentication(this);
        setContentView(R.layout.main);

        pullToRefreshAttacher = PullToRefreshAttacher.get(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean res;
        switch (item.getItemId()) {
            case R.id.action_open_uri:
                res = true;
                openUri();
                break;
            case R.id.action_sign_out:
                res = true;
                signOut();
                break;
            default:
                res = super.onOptionsItemSelected(item);
                break;
        }
        return res;
    }

    private void openUri() {
        Uri uri = Uri.parse("http://animetick.net");
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    private void signOut() {
        final Networking networking = new Networking(authentication);
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                String path = "/sign_out";
                try {
                    networking.get(path);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSucceeded) {
                super.onPostExecute(isSucceeded);
                if (isSucceeded) {
                    authentication.removeSessionId();
                    authentication.removeCsrfToken();
                    moveToAuthenticationActivity();
                    finish();
                }
            }
        };
        task.execute();
    }

    private void moveToAuthenticationActivity() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }

    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            switch (position) {
                case 0:
                    return new TicketListFragment();
                case 1:
                    return new AnimeListFragment();
                default:
                    break;
            }

            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            //fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // TODO: Implements more fragments.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2: // TODO: Not implements
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply
     * displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main_dummy, container, false);
        }
    }

    public PullToRefreshAttacher getPullToRefreshAttacher() {
        return this.pullToRefreshAttacher;
    }

}

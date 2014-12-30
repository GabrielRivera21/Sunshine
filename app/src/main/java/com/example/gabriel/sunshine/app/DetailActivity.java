package com.example.gabriel.sunshine.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.support.v7.widget.ShareActionProvider;

import org.w3c.dom.Text;

import java.io.File;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settings = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();
        private String ForeCastStr;
        private ShareActionProvider mShareActionProvider;

        public PlaceholderFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent detailForecast = getActivity().getIntent();
            ForeCastStr = detailForecast.getStringExtra("Day");
            TextView textView = (TextView) rootView.findViewById(R.id.detail_forecast);
            textView.setText(ForeCastStr);

            return rootView;
        }

        private Intent createShareIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, ForeCastStr + "#SunshineApp");
            return shareIntent;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
            inflater.inflate(R.menu.detailfragment, menu);
            MenuItem menuItem = menu.findItem(R.id.action_share);
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if(mShareActionProvider != null){
                mShareActionProvider.setShareIntent(createShareIntent());
            }else {
                Log.d(LOG_TAG, "mShareActionProvider null.");
            }
        }
    }
}

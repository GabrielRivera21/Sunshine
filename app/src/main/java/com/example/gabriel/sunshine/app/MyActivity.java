package com.example.gabriel.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if(id == R.id.action_map){
            openPreferredLocationOnMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationOnMap() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplication());
        String location = pref.getString(getString(R.string.pref_location_key),
                                        getString(R.string.pref_location_value));

        Uri geoLocation = Uri.parse("0,0?").buildUpon().appendQueryParameter("q", location).build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Log.d("MyActivity", "There is no app that can run this activity for this location: "
                    + location);
        }
    }



}

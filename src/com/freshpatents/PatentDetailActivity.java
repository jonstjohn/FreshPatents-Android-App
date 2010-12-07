package com.freshpatents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Area activity - tabs and content
 */
public class PatentDetailActivity extends Activity {
    
    /**
     * Area id
     */
    private String patentId;
    
    /**
     * Name
     */
    private String name = "";
    
    /**
     * Progress dialog
     */
    private ProgressDialog dialog;
    
    private Context mContext;
    
    private String tempUnit;
    
    /** 
     * On create 
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras(); 
        patentId = extras.getString("patentId");
        
        setContentView(R.layout.patent_detail);
        
        mContext = this;
        
        loadContent();
        
    }
    
    public void loadContent()
    {
        String url = "/search.php?a=1&p=" + patentId;
        
        // Show loading dialog
        dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        
        // async task
        new GetJsonTask().execute(url);
    }
    
    /**
     * On pause activity
     */
    public void onPause()
    {
        super.onPause();
        dialog.dismiss();
    }
    
    public void onDestroy()
    {
        super.onDestroy();
        dialog.dismiss();
    }
    
    public void onStop()
    {
        super.onStop();
        dialog.dismiss();
    }
    
    public void onResume()
    {
        super.onResume();
        /*
        
        // If tempUnit has changed, refresh
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (!prefs.getString("tempUnit", "f").equals(tempUnit)) {
            refresh();
        }
        */
    }
    
    /**
     * Refresh activity
     */
    /*
    private void refresh()
    {
        Intent refreshIntent = new Intent(getApplicationContext(), PatentDetailActivity.class);
        refreshIntent.putExtra("patentId", patentId);
        refreshIntent.putExtra("name", name);
        startActivity(refreshIntent);
        this.getParent().finish();
    }
    */
    

    /**
     * Create menu options
     */
    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.area_menu, menu);
        MenuItem fav = menu.findItem(R.id.favorite);
        
        if (isFavorite()) {
            
            fav.setTitle("Remove Favorite");
            fav.setIcon(R.drawable.btn_star_big_off);

        } else {
            
            fav.setTitle("Add Favorite");
            fav.setIcon(R.drawable.btn_star_big_on);

        }
        return true;
    }
    */
    
    /**
     * Handle menu clicks
     */
    /*
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.favorite:
            if (isFavorite()) {
                removeFavorite();
            } else {
                saveFavorite();
            }
            return true;
        case R.id.refresh:
            refresh();
            return true;
        case R.id.home:
            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(homeIntent);
            return true;
        case R.id.settings:
            Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return false;

    }
    */
    
    /**
     * Setup menu dynamically
     */
    /*
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem fav = menu.findItem(R.id.favorite);
        
        if (isFavorite()) {
            fav.setTitle("Remove Favorite");
            fav.setIcon(R.drawable.btn_star_big_off);
        } else {
            fav.setTitle("Add Favorite");
            fav.setIcon(R.drawable.btn_star_big_on);
        }
        return true;
    }
    */
    
    /**
     * Save favorite area
     */
    /*
    public void saveFavorite()
    {
        FavoriteDbAdapter dbAdapter = new FavoriteDbAdapter(this);
        dbAdapter.open();
        dbAdapter.addFavorite(Integer.parseInt(patentId), name);
        
    }
    */
    
    /**
     * Remove favorite area
     */
    /*
    public void removeFavorite() {
        
        FavoriteDbAdapter dbAdapter = new FavoriteDbAdapter(this);
        dbAdapter.open();
        dbAdapter.removeFavorite(Integer.parseInt(patentId));
        
    }
    */
    
    /**
     * Check to see if area is already a favorite
     */
    /*
    public boolean isFavorite() {

        FavoriteDbAdapter dbAdapter = new FavoriteDbAdapter(this);
        dbAdapter.open();
        boolean isFavorite = dbAdapter.isFavorite(Integer.parseInt(patentId));
        return isFavorite;

    }
    */
    
    /**
     * Asynchronous task to retrieve JSON
     */
    private class GetJsonTask extends AsyncTask<String, Void, String> {
        
        /**
         * Execute in background
         */
        protected String doInBackground(String... args) {
            FpApi api = new FpApi(mContext);
            return api.getJson(args[0]);
        }
        
        /**
         * After execute, handle in UI thread
         */
        protected void onPostExecute(String result) {
            
            loadPatent(result);
            
        }
    }
    
    /**
     * Load forecast from JSON
     */
    private void loadPatent(String result) {

        //TableLayout table = (TableLayout) findViewById(R.id.forecast_table);
        
        //LayoutInflater inflater = getLayoutInflater();
        
        try {
            
            JSONArray jsonArray = new JSONArray(result);
            
            JSONObject p = jsonArray.getJSONObject(0);
            name = p.getString("ApplicationTitle");
            String pAbstract = p.getString("ApplicationAbstract");
            String dateStr = p.getString("ApplicationDate");
            String date = dateStr.substring(4, 6) + "/" + dateStr.substring(6, 8) + "/" + dateStr.substring(0, 4);
            
            Log.i("FP", name);
            Log.i("FP", pAbstract);
            ((TextView)findViewById(R.id.title)).setText(name);
            ((TextView)findViewById(R.id.pAbstract)).setText(pAbstract);
            ((TextView)findViewById(R.id.date)).setText(date);
            //JSONArray forecastData = jsonObj.getJSONArray("f");
            

            
        } catch (JSONException e) {
            
            Toast.makeText(mContext, "An error occurred while retrieving forecast data", Toast.LENGTH_SHORT).show();
            
        }
        
        dialog.hide();
        
    }
}


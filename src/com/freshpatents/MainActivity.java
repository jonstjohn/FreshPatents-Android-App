package com.freshpatents;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
    
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        // Search text
        final EditText searchEdit = (EditText) findViewById(R.id.search_edit);

        // Capture key actions
        searchEdit.setOnKeyListener(new OnKeyListener() {
            
            /**
             * Capture key actions
             */
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                
                // If enter is pressed, do search
                if ((event.getAction() == KeyEvent.ACTION_DOWN) 
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    doSearch();
                    return true;
                    
                }
                
                return false;
            }
        });
        
        // Search button
        ImageView searchButton = (ImageView) findViewById(R.id.search_button);
        searchButton.setOnClickListener(searchListener);
        
    }
    
    /**
     * On click listener for favorites button
     */
    private OnClickListener searchListener = new OnClickListener() {
        
        public void onClick(View v) {
            
            doSearch();

        }
        
    };
    
    private boolean doSearch() {

        EditText searchEdit = (EditText) findViewById(R.id.search_edit);
        String srch = searchEdit.getText().toString();
        srch = URLEncoder.encode(srch);
        
        // Clean-up search string - URL encode
        Intent i = new Intent(getApplicationContext(), PatentListActivity.class);
        i.putExtra("srch", srch);
        startActivity(i);
        return true;
        
    }

}

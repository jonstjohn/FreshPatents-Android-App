package com.freshpatents;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

/**
 * Handle ClimbingWeather.com api requests
 */
public class FpApi {
    
    /**
     * Android activity context
     */
    private Context mContext;
    
    /**
     * Base URL
     */
    private String mBaseUrl = "http://api.freshpatents.com";
    
    /**
     * Constructor
     * @param context
     */
    public FpApi(Context context)
    {
        mContext = context;
    }
    
    /**
     * Get JSON from API
     * @param url
     * @return
     */
    public String getJson(String url)
    {
        String apiKey = getApiKey();
        
        String divider = url.contains("?") ? "&" : "?";

        // http://api.freshpatents.com/search.php?s=software+system&a=1
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        //String absoluteUrl = mBaseUrl + url + divider + "apiKey=" + apiKey + "&tempUnit=" + prefs.getString("tempUnit", "f") +
        //    "&device=android&version=1.0";
        String absoluteUrl = mBaseUrl + url + divider + "apiKey=" + apiKey;
        
        Log.i("FP", absoluteUrl);
        HttpToJson toJson = new HttpToJson();
        return toJson.getJsonFromUrl(absoluteUrl);
        
    }
    
    /**
     * Get API key
     * @return
     */
    private String getApiKey()
    {
        String apiKey = "android-";
        
        String androidId = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);
        
        if (androidId == null) {
            androidId = "unknown";
        }
        
        apiKey += androidId;
        return apiKey;
    }

}

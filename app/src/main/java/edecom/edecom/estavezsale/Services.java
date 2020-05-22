package edecom.edecom.estavezsale;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by Ailu on 12/3/2018.
 */

public class Services extends TabActivity {


    //Actions
    public static final String SCAN = "la.droid.qr.scan";
    public static final String ENCODE = "la.droid.qr.encode";
    public static final String DECODE = "la.droid.qr.decode";

    //Parameters
    //SCAN / DECODE
    public static final String COMPLETE = "la.droid.qr.complete"; //Default: false
    //ENCODE
    public static final String CODE =  "la.droid.qr.code"; //Required
    public static final String SIZE = "la.droid.qr.size"; //Default: Fit screen
    //ENCODE / DECODE
    public static final String IMAGE =  "la.droid.qr.image"; //Default for encode: false / Required for decode

    //Result
    public static final String RESULT = "la.droid.qr.result";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);

        //Recycled objects
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        //Scan Activity
        intent = new Intent().setClass(this, Scan.class);
        spec = tabHost.newTabSpec("Scan").setIndicator("", res.getDrawable(R.drawable.camera)).setContent(intent);
        tabHost.addTab(spec);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Nothing
    }
}

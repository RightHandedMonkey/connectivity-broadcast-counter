package com.rhm.cbc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sambo on 11/11/17.
 */

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //This is just a placeholder to make sure the application is alive so that
        //Connectivity actions are registered
        Log.d("SAMB", "BootCompleteReceiver event received. App: "+ CBCApplication.class.getName()+ " Should have started");
    }
}
package com.rhm.cbc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rhm.cbc.CBCApplication;

/**
 * Created by sambo on 11/11/17.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SAMB", "BootCompleteReceiver event received. App: "+ CBCApplication.class.getName()+ " Should have started");

    }
}
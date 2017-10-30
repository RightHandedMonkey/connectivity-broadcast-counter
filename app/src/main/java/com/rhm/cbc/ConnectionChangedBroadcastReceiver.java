package com.rhm.cbc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.Date;

import com.rhm.cbc.data.model.ChangeEvent;

/**
 * Created by sambo on 10/27/17.
 */

class ConnectionChangedBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CBC", "onReceive called");
        CBCApplication app = (CBCApplication) context.getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager wm = (WifiManager) app.getSystemService(Context.WIFI_SERVICE);
        NetworkInfo curNetInfo = cm.getActiveNetworkInfo();
        //curNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
        //curNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;

        String msg = curNetInfo != null ? curNetInfo.toString() : "";
        String ssid = wm.getConnectionInfo() == null ? "" : wm.getConnectionInfo().getSSID();
        String mDetailedState = curNetInfo != null ? curNetInfo.getDetailedState().name() : "";
        String typeName = curNetInfo != null ? curNetInfo.getTypeName() : "";
        String time = new Date().toString();

        ChangeEvent ce = new ChangeEvent();
        ce.setCompleteMsg(msg);
        ce.setSsid(ssid);
        ce.setTypeName(typeName);
        ce.setDetailedState(mDetailedState);
        ce.setEventTime(time);


    }
}


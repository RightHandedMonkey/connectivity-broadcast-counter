package com.rhm.cbc;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.rhm.cbc.data.CBCDatabase;
import com.squareup.leakcanary.LeakCanary;
import com.tspoon.traceur.Traceur;

import com.rhm.cbc.injection.component.AppComponent;
import com.rhm.cbc.injection.component.DaggerAppComponent;
import com.rhm.cbc.injection.module.AppModule;
import com.rhm.cbc.injection.module.NetworkModule;
import timber.log.Timber;

public class CBCApplication extends Application {

    private AppComponent appComponent;

    public static CBCApplication get(Context context) {
        return (CBCApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
            Traceur.enableLogging();
        }
        CBCDatabase.getInstance(getApplicationContext());

    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .networkModule(new NetworkModule(this, BuildConfig.POKEAPI_API_URL))
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}

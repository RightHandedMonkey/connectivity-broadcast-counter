package com.rhm.cbc.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.rhm.cbc.data.DataManager;
import com.rhm.cbc.injection.ApplicationContext;
import com.rhm.cbc.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();
}

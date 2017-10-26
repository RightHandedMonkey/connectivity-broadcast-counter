package com.rhm.cbc.injection.component;

import dagger.Subcomponent;
import com.rhm.cbc.features.detail.DetailActivity;
import com.rhm.cbc.features.main.MainActivity;
import com.rhm.cbc.injection.PerActivity;
import com.rhm.cbc.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);
}

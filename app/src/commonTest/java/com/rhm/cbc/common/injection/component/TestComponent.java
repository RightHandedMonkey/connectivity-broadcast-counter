package com.rhm.cbc.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.rhm.cbc.common.injection.module.ApplicationTestModule;
import com.rhm.cbc.injection.component.AppComponent;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}

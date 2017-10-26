package com.rhm.cbc.injection.component;

import dagger.Subcomponent;
import com.rhm.cbc.injection.PerFragment;
import com.rhm.cbc.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}

package com.alex.yanovich.booksmobidev.injection.component;

import com.alex.yanovich.booksmobidev.MainActivity;
import com.alex.yanovich.booksmobidev.injection.PerActivity;
import com.alex.yanovich.booksmobidev.injection.module.ActivityModule;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}

package com.alex.yanovich.booksmobidev.injection.component;

import android.app.Application;
import android.content.Context;

import com.alex.yanovich.booksmobidev.injection.ApplicationContext;
import com.alex.yanovich.booksmobidev.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();

}
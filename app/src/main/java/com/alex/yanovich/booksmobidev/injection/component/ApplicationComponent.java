package com.alex.yanovich.booksmobidev.injection.component;

import android.app.Application;
import android.content.Context;

import com.alex.yanovich.booksmobidev.data.DataManager;
import com.alex.yanovich.booksmobidev.data.SyncService;
import com.alex.yanovich.booksmobidev.data.local.DatabaseHelper;
import com.alex.yanovich.booksmobidev.data.remote.RetrofitService;
import com.alex.yanovich.booksmobidev.injection.ApplicationContext;
import com.alex.yanovich.booksmobidev.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();
    Application application();
    RetrofitService retrofitService();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
}
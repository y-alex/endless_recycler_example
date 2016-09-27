package com.alex.yanovich.booksmobidev;


import android.app.Application;
import android.content.Context;

import com.alex.yanovich.booksmobidev.injection.component.ApplicationComponent;
import com.alex.yanovich.booksmobidev.injection.component.DaggerApplicationComponent;
import com.alex.yanovich.booksmobidev.injection.module.ApplicationModule;

import timber.log.Timber;

public class BooksApplication extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            //Fabric.with(this, new Crashlytics());
        }
    }

    public static BooksApplication get(Context context) {
        return (BooksApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}

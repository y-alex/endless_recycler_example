package com.alex.yanovich.booksmobidev.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alex.yanovich.booksmobidev.BooksApplication;
import com.alex.yanovich.booksmobidev.injection.component.ActivityComponent;
import com.alex.yanovich.booksmobidev.injection.component.DaggerActivityComponent;
import com.alex.yanovich.booksmobidev.injection.module.ActivityModule;


public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(BooksApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }
}

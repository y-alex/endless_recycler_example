package com.alex.yanovich.booksmobidev.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alex.yanovich.booksmobidev.R;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.alex.yanovich.booksmobidev.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";
    @Inject
    MainPresenter mMainPresenter;
    @Inject BooksAdapter mBooksAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

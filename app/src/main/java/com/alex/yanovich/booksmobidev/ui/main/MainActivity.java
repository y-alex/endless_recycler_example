package com.alex.yanovich.booksmobidev.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.alex.yanovich.booksmobidev.R;
import com.alex.yanovich.booksmobidev.data.SyncService;
import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView{

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
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setAdapter(mBooksAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        mMainPresenter.attachView(this);
        mMainPresenter.loadBooks();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void showBooks(List<Item> allItems) {
        mBooksAdapter.setItems(allItems);
        mBooksAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBooksEmpty() {
        mBooksAdapter.setItems(new ArrayList<>());
        mBooksAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        showToast("There was a problem loading the books");
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

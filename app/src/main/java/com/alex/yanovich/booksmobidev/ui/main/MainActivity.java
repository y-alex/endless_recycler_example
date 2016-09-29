package com.alex.yanovich.booksmobidev.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.alex.yanovich.booksmobidev.R;
import com.alex.yanovich.booksmobidev.data.SyncService;
import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView,SearchView.OnQueryTextListener{

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.alex.yanovich.booksmobidev.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";
    public static final String EXTRA_INTENT_SERVICE_REQUEST = "com.alex.yanovich.booksmobidev.ui.main.MainActivity.REQUEST";
    @Inject
    MainPresenter mMainPresenter;
    @Inject BooksAdapter mBooksAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.main_activity_toolbar_title);
        getSupportActionBar().setIcon(R.drawable.ic_books);

        mRecyclerView.setAdapter(mBooksAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StaggeredGridLayoutManager sgLayoutManager = new StaggeredGridLayoutManager(2, 1);
        mRecyclerView.setLayoutManager(sgLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(sgLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Timber.i("onLoadMore method has been triggered, End of LIST");
            }
        });

        mMainPresenter.attachView(this);
        mMainPresenter.loadBooks();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_one:
                showToast("Clicked menu one");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showToast(query);
        startServiceRequest(query);
        // Hide the keyboard and give focus to the list
       // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       // imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
       // mRecyclerView.requestFocus();

        mSearchView.clearFocus();
        return true;
    }

    private void startServiceRequest(String request){
        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            Intent intent = SyncService.getStartIntent(this);
            intent.putExtra(EXTRA_INTENT_SERVICE_REQUEST, request);
            startService(intent);
        }
    }

    /* -----------------Methods of MainMvpView----------------------- */
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

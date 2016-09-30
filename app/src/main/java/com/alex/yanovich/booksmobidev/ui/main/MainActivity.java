package com.alex.yanovich.booksmobidev.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alex.yanovich.booksmobidev.R;
import com.alex.yanovich.booksmobidev.data.SyncService;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, SearchView.OnQueryTextListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "com.alex.yanovich.booksmobidev.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";
    public static final String EXTRA_INTENT_SERVICE_REQUEST = "com.alex.yanovich.booksmobidev.ui.main.MainActivity.REQUEST";
    public static final String EXTRA_INTENT_SERVICE_REQUEST_CODE = "com.alex.yanovich.booksmobidev.ui.main.MainActivity.REQUEST_CODE";
    public static final int EXTRA_INTENT_SERVICE_CODE_FIRST_LOAD = 0;
    public static final int EXTRA_INTENT_SERVICE_CODE_LOAD_MORE = 1;
    public static final String KEY_BUNDLE_ONSAVE_REQUEST = "com.alex.yanovich.booksmobidev.ui.main.MainActivity.KEY_BUNDLE_ONSAVE_REQUEST";
    @Inject
    MainPresenter mMainPresenter;
    @Inject
    BooksAdapter mBooksAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    SearchView mSearchView;
    private String mCurrentRequest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            String request = savedInstanceState.getString(KEY_BUNDLE_ONSAVE_REQUEST);
            mCurrentRequest = request;
        }

        //Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.main_activity_toolbar_title);
        getSupportActionBar().setIcon(R.drawable.ic_books);

        mRecyclerView.setAdapter(mBooksAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StaggeredGridLayoutManager sgLayoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            sgLayoutManager = new StaggeredGridLayoutManager(3, 1);
        } else {
            sgLayoutManager = new StaggeredGridLayoutManager(2, 1);
        }

        mRecyclerView.setLayoutManager(sgLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(sgLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Timber.i("onLoadMore method has been triggered, End of LIST");
                startServiceRequest(mCurrentRequest, EXTRA_INTENT_SERVICE_CODE_LOAD_MORE);
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
        switch (item.getItemId()) {
            case R.id.menu_one:
                showToast("Clicked menu one");
                startServiceRequest("niel gaiman", EXTRA_INTENT_SERVICE_CODE_LOAD_MORE);
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
        mCurrentRequest = query;
        //first we need clear our recyclerView(look showBooks() method for details)
        showBooksEmpty();
        startServiceRequest(query, EXTRA_INTENT_SERVICE_CODE_FIRST_LOAD);

        // Hide the keyboard and give focus to the list
        // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        // mRecyclerView.requestFocus();

        mSearchView.clearFocus();
        return true;
    }

    /*   Save request when activity recreates */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_BUNDLE_ONSAVE_REQUEST, mCurrentRequest);
        super.onSaveInstanceState(outState);
    }

    /*
    This method prepare intent and start service that will download information we want
     */
    private void startServiceRequest(String request, int requestCode) {
        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            Intent intent = SyncService.getStartIntent(this);
            intent.putExtra(EXTRA_INTENT_SERVICE_REQUEST, request);
            intent.putExtra(EXTRA_INTENT_SERVICE_REQUEST_CODE, requestCode);
            startService(intent);
        }
    }

    /* -----------------Methods of MainMvpView----------------------- */
    @Override
    public void showBooks(List<Item> allItems) {
        //Need add only items that no in the list,
        //this operation need to avoid in the future:
        //                     need implement different structure with better knowledge of rxjava
        //                    Now I working on agile not coupled structure with testable goodness

        //we will start from the end of the new list, only we find first item that exist in adapter list we will break
        List<Item> adapterList = mBooksAdapter.getAdapterList();
        for (int i = allItems.size() - 1; i >= 0; i--) {
            Item itemToCheck = allItems.get(i);
            if (!adapterList.contains(itemToCheck)) {
                adapterList.add(itemToCheck);
            } else {
                break;
            }
        }
        mBooksAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBooksEmpty() {
        mBooksAdapter.setItems(new ArrayList<Item>());
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

package com.alex.yanovich.booksmobidev.data;

import com.alex.yanovich.booksmobidev.data.local.DatabaseHelper;
import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.data.remote.RetrofitService;
import com.alex.yanovich.booksmobidev.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import timber.log.Timber;


@Singleton
public class DataManager {

    private final RetrofitService mRetrofitService;
    private final DatabaseHelper mDatabaseHelper;
    private int mTotalItems;
    private int mStartIndex;

    @Inject
    public DataManager(RetrofitService retrofitService, DatabaseHelper databaseHelper) {
        mRetrofitService = retrofitService;
        mDatabaseHelper = databaseHelper;
    }

    public Observable<Item> syncItemsTest(String request, int requestCode) {
        if(requestCode == MainActivity.EXTRA_INTENT_SERVICE_CODE_LOAD_MORE){
            return syncItemsMore(request);
        }else {
            return syncItems(request);
        }
    }

    public Observable<Item> syncItems(String request) {
        return mRetrofitService.getAllVolumes(request,0).map(new Func1<AllVolumes, List<Item>>() {
            @Override
            public List<Item> call(AllVolumes allVolumes) {
                mTotalItems = allVolumes.getTotalItems();
                mStartIndex = 0;
                return allVolumes.getItems();
            }
        }).concatMap(new Func1<List<Item>, Observable<Item>>() {
                    @Override
                    public Observable<Item> call(List<Item> items) {
                        return mDatabaseHelper.setItems(items);
                    }
                });
    }

    public Observable<Item> syncItemsMore(String request) {
        Observable<Item> itemObservable;
        mStartIndex+=30;
        if(mStartIndex < mTotalItems){
            itemObservable = mRetrofitService.getAllVolumes(request, mStartIndex).map(new Func1<AllVolumes, List<Item>>() {
                @Override
                public List<Item> call(AllVolumes allVolumes) {
                    return allVolumes.getItems();
                }
            }).concatMap(new Func1<List<Item>, Observable<Item>>() {
                @Override
                public Observable<Item> call(List<Item> items) {
                    Timber.i("Count of items load more was:" + items.size());
                    return mDatabaseHelper.setItemsMore(items);
                }
            });
        }else {
            itemObservable = mDatabaseHelper.setItemsMore(new ArrayList<Item>());
        }
        return itemObservable;
    }

    public Observable<List<Item>> getItems() {
        return mDatabaseHelper.getItems().distinct();
    }

}

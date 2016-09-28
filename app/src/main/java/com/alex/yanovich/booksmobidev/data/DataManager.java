package com.alex.yanovich.booksmobidev.data;

import com.alex.yanovich.booksmobidev.data.local.DatabaseHelper;
import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.data.remote.RetrofitService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;


@Singleton
public class DataManager {

    private final RetrofitService mRetrofitService;
    private final DatabaseHelper mDatabaseHelper;

    @Inject
    public DataManager(RetrofitService retrofitService, DatabaseHelper databaseHelper) {
        mRetrofitService = retrofitService;
        mDatabaseHelper = databaseHelper;
    }

    public Observable<Item> syncItems() {
        return mRetrofitService.getAllVolumes().map(new Func1<AllVolumes, List<Item>>() {
            @Override
            public List<Item> call(AllVolumes allVolumes) {
                return allVolumes.getItems();
            }
        }).concatMap(new Func1<List<Item>, Observable<Item>>() {
                    @Override
                    public Observable<Item> call(List<Item> items) {
                        return mDatabaseHelper.setItems(items);
                    }
                });
    }

    public Observable<List<Item>> getItems() {
        return mDatabaseHelper.getItems().distinct();
    }

}

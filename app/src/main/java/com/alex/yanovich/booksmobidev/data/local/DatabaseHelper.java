package com.alex.yanovich.booksmobidev.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alex.yanovich.booksmobidev.data.model.ImageLinks;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.data.model.VolumeInfo;
import com.alex.yanovich.booksmobidev.ui.main.MainActivity;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        mDb = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    public Observable<Void> clearTables() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    Cursor cursor = mDb.query("SELECT name FROM sqlite_master WHERE type='table'");
                    while (cursor.moveToNext()) {
                        mDb.delete(cursor.getString(cursor.getColumnIndex("name")), null);
                    }
                    cursor.close();
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<Item> setItems(final Collection<Item> newItems, final int requestCode) {
        return Observable.create(new Observable.OnSubscribe<Item>() {
            @Override
            public void call(Subscriber<? super Item> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    //Clear database when first load
                    if(requestCode == MainActivity.EXTRA_INTENT_SERVICE_CODE_FIRST_LOAD){
                        mDb.delete(DbContract.VolumesBooksTable.TABLE_NAME, null);
                    }
                    for (Item item : newItems) {
                        long result = mDb.insert(DbContract.VolumesBooksTable.TABLE_NAME,
                                DbContract.VolumesBooksTable.toContentValues(item),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) subscriber.onNext(item);
                        Timber.i("Insert to database more results:"+ result);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Item>> getItems() {
        return mDb.createQuery(DbContract.VolumesBooksTable.TABLE_NAME,
                "SELECT * FROM " + DbContract.VolumesBooksTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Item>() {
                    @Override
                    public Item call(Cursor cursor) {
                        Item item = DbContract.VolumesBooksTable.parseCursor(cursor);
                        Timber.i("Parse cursor:"+cursor.getPosition()+":"+item.getVolumeInfo().getTitle());
                        return  DbContract.VolumesBooksTable.parseCursor(cursor);
                    }
                });
    }

}
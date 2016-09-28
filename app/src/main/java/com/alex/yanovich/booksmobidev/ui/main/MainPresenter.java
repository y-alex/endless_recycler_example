package com.alex.yanovich.booksmobidev.ui.main;

import com.alex.yanovich.booksmobidev.data.DataManager;
import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.data.model.VolumeInfo;
import com.alex.yanovich.booksmobidev.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class MainPresenter extends BasePresenter<MainMvpView> {
    private Subscription mSubscription;
    private final DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadBooks() {
        checkViewAttached();
        List<Item> allBooks = getFakeList();

        if (allBooks.isEmpty()) {
            getMvpView().showBooksEmpty();
        } else {
            getMvpView().showBooks(allBooks);
        }
    }

    public List<Item> getFakeList(){
        List<Item> list = new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            Item item = new Item();
            VolumeInfo vi = new VolumeInfo();
            vi.setTitle("Title of the book"+i);
            item.setVolumeInfo(vi);

            list.add(item);
        }

        return list;
    }

}


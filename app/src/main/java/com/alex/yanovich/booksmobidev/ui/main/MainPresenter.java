package com.alex.yanovich.booksmobidev.ui.main;

import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.alex.yanovich.booksmobidev.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import rx.Subscription;

public class MainPresenter extends BasePresenter<MainMvpView> {
    private Subscription mSubscription;

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
        List<AllVolumes> allVolumes = new ArrayList<>();

        if (allVolumes.isEmpty()) {
            getMvpView().showBooksEmpty();
        } else {
            getMvpView().showBooks(allVolumes);
        }
    }

}


package com.alex.yanovich.booksmobidev.ui.base;


public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
package com.alex.yanovich.booksmobidev.ui.main;

import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.ui.base.MvpView;

import java.util.List;


public interface MainMvpView extends MvpView {

    void showBooks(List<Item> allItems);

    void showBooksEmpty();

    void showError();

}

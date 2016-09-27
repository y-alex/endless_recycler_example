package com.alex.yanovich.booksmobidev.ui.main;

import com.alex.yanovich.booksmobidev.data.model.Book;
import com.alex.yanovich.booksmobidev.ui.base.MvpView;

import java.util.List;


public interface MainMvpView extends MvpView {

    void showBooks(List<Book> books);

    void showBooksEmpty();

    void showError();

}

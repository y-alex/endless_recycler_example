package com.alex.yanovich.booksmobidev.data.remote;

import com.alex.yanovich.booksmobidev.data.model.AllVolumes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface RetrofitService {
    String ENDPOINT = "https://www.googleapis.com/books/v1/";

    @GET("volumes?maxResults=30&startIndex=0&" +
            "fields=kind,totalItems,items(volumeInfo/title,volumeInfo/infoLink,volumeInfo/imageLinks(smallThumbnail))")
    Observable<AllVolumes> getAllVolumes(@Query("q")  String request);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static RetrofitService newRetrofitService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxAdapter)
                    .build();
            return retrofit.create(RetrofitService.class);
        }
    }
}

package com.alex.yanovich.booksmobidev.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.alex.yanovich.booksmobidev.data.model.ImageLinks;
import com.alex.yanovich.booksmobidev.data.model.Item;
import com.alex.yanovich.booksmobidev.data.model.VolumeInfo;


public class DbContract {
    public abstract static class VolumesBooksTable implements BaseColumns{
        public static final String TABLE_NAME = "volumes_books";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE_LINK = "image_link";
        public static final String COLUMN_INFO_LINK = "info_link";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_IMAGE_LINK + " TEXT NOT NULL, " +
                        COLUMN_INFO_LINK + " TEXT NOT NULL" +
                        " ); ";
        public static ContentValues toContentValues(Item item) {
            ContentValues values = new ContentValues();
                values.put(COLUMN_TITLE, item.getVolumeInfo().getTitle());
                values.put(COLUMN_IMAGE_LINK, item.getVolumeInfo().getImageLinks().getSmallThumbnail());
                values.put(COLUMN_INFO_LINK, item.getVolumeInfo().getInfoLink());

            return values;
        }
        public static Item parseCursor(Cursor cursor) {
            Item item = new Item();
            //First create volumeInfo and add title
            VolumeInfo volumeInfo = new VolumeInfo();
            volumeInfo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
            volumeInfo.setInfoLink(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INFO_LINK)));
            //Create ImageLinks and add image url
            ImageLinks imageLinks = new ImageLinks();
            imageLinks.setSmallThumbnail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_LINK)));

            volumeInfo.setImageLinks(imageLinks);

            item.setVolumeInfo(volumeInfo);

            return item;
        }
    }
}

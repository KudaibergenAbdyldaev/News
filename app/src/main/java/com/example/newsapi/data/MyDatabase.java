package com.example.newsapi.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newsapi.pojo.Article;

@Database(entities = {Article.class}, version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DB_NAME = "news.db";
    private static MyDatabase database;

    private static final Object LOCK = new Object();

    public static MyDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, MyDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
            return database;
        }
    }

    public abstract MyDao myDao();

}

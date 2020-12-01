package com.example.newsapi.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapi.pojo.Article;

import java.util.List;
@Dao
public interface MyDao {
    @Query("SELECT * FROM news")
    LiveData<List<Article>> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNews(List<Article> employees);

    @Query("DELETE FROM news")
    void deleteAllNews();
}

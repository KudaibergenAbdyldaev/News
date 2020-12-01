package com.example.newsapi.screen;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapi.data.MyDatabase;
import com.example.newsapi.network.ApiInterface;
import com.example.newsapi.network.NewsRepository;
import com.example.newsapi.pojo.Article;
import com.example.newsapi.pojo.News;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class NewsViewModel extends AndroidViewModel {

    private static MyDatabase db;
    private LiveData<List<Article>> liveData;
    private MutableLiveData<Throwable> errors;
    private CompositeDisposable compositeDisposable;
    public static final String API_KEY = "c4b506d5dbe34ca6ba4f18fb8deb721d";

    public NewsViewModel(@NonNull Application application) {
        super(application);
        db = MyDatabase.getInstance(application);
        liveData = db.myDao().getAllNews();
        errors = new MutableLiveData<>();
        loadData();
    }
    public LiveData<Throwable> getErrors() {
        return errors;
    }

    public void clearErrors() {
        errors.setValue(null);
    }
    public LiveData<List<Article>> getLiveData() {
        return liveData;
    }

    @SuppressWarnings("unchecked")
    private void insertNews(List<Article> articles) {
        new InsertNewsTask().execute(articles);
    }

    private static class InsertNewsTask extends AsyncTask<List<Article>, Void, Void> {
        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Article>... lists) {
            if (lists != null && lists.length > 0) {
                db.myDao().insertNews(lists[0]);
            }
            return null;
        }
    }

    public void deleteAllNews(){
        new DeleteAllNewsTask().execute();
    }

    public static class DeleteAllNewsTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            db.myDao().deleteAllNews();
            return null;
        }
    }

    public void loadData() {
        NewsRepository apiFactory = NewsRepository.getInstance();
        ApiInterface apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getNewsList("us", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(employeeResponse -> {
                    deleteAllNews();
                    insertNews(employeeResponse.getArticles());
                }, throwable -> {
                    errors.setValue(throwable);
                    Log.i("error", " "+ throwable);
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

}

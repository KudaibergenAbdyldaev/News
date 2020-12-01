package com.example.newsapi.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.newsapi.R;
import com.example.newsapi.detail.DetailActivity;
import com.example.newsapi.network.ApiInterface;
import com.example.newsapi.network.NewsRepository;
import com.example.newsapi.pojo.Article;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private NewsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        viewModel = ViewModelProviders.of(this ).get(NewsViewModel.class);
        loadCache();
        viewModel.getErrors().observe(this, throwable -> {
            if (throwable != null) {
                Log.i("error", " "+ throwable);
                viewModel.clearErrors();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                viewModel.loadData();
                swipeRefreshLayout.setRefreshing(false);
            }, 500);
        });

    }

    private void loadCache(){
        viewModel.getLiveData().observe(this, articles -> {
            adapter = new MyAdapter(MainActivity.this, articles);
            recyclerView.setAdapter(adapter);
            adapter.setOnClickListener(position -> {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("title", articles.get(position).getTitle());
                intent.putExtra("desc", articles.get(position).getDescription());
                intent.putExtra("data", articles.get(position).getPublishedAt());
                intent.putExtra("author", articles.get(position).getAuthor());
                intent.putExtra("img", articles.get(position).getUrlToImage());
                intent.putExtra("url",articles.get(position).getUrl());
                startActivity(intent);
            });
        });
    }

}
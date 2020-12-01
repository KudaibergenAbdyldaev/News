package com.example.newsapi.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapi.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView title, desc, author, content, data;
    ImageView img;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        author = (TextView) findViewById(R.id.author);
        content = (TextView) findViewById(R.id.content);
        data = (TextView) findViewById(R.id.publishedAt);
        button = (Button) findViewById(R.id.button);
        img = (ImageView) findViewById(R.id.img);

        final Intent intent = getIntent();

        String name = intent.getStringExtra("title");
        if (name != null) {
            title.setText(name);
        } else {
            title.setVisibility(View.GONE);
        }

        String desc1 = intent.getStringExtra("desc");
        if (desc1 != null) {
            desc.setText(desc1);
        } else {
            desc.setVisibility(View.GONE);
        }

        String author1 = intent.getStringExtra("author");
        if (author1 != null) {
            author.setText("Author: " + author1);
        } else {
            author.setVisibility(View.GONE);
        }

        String data1 = intent.getStringExtra("data");
        data.setText(data1);


        String image_url = intent.getStringExtra("img");
        Picasso.get()
                .load(image_url)
                .error(R.drawable.ic_baseline_error_24)
                .fit()
                .centerCrop()
                .into(img);

        final String url = intent.getStringExtra("url");
        // Если нажать на кнопку, то мы переходим в Chrome, что узнать по больше информации
        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse(url));
            startActivity(intent1);
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
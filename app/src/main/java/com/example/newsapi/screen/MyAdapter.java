package com.example.newsapi.screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapi.R;
import com.example.newsapi.pojo.Article;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    List<Article> articles;
    OnItemClickListener clickListener;

    public MyAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.title.setText(articles.get(position).getTitle());
        holder.desc.setText(articles.get(position).getDescription());
        Picasso.get()
                .load(articles.get(position).getUrlToImage())
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title, desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.img);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (clickListener != null){
                    if (position != RecyclerView.NO_POSITION){
                        clickListener.onItemClick(position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(OnItemClickListener listener){
        clickListener = listener;
    }
}

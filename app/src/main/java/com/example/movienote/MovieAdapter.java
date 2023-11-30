package com.example.movienote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    List<MovieItem> array;
    Context context;

    public MovieAdapter(List<MovieItem> array, Context context){
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movieitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        String image=array.get(position).getImage();
        String title=array.get(position).getTitle();
        holder.textTitle.setText(title);

        Glide.with(context)
                .load(image)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView textTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle=itemView.findViewById(R.id.movieTitle);
            image=itemView.findViewById(R.id.moviePoster);
        }
    }
}
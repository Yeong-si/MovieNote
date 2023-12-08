package com.example.movienote;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
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
    String fromActivity;
    private OnClickListener onClickListener;


    public MovieAdapter(List<MovieItem> array, Context context,String fromActivity){
        this.array = array;
        this.context = context;
        this.fromActivity = fromActivity;
    }


    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movieitem,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MovieItem item = array.get(position);
        String image=array.get(position).getImage();
        String title=array.get(position).getTitle();
        holder.textTitle.setText(title);

        Glide.with(context)
                .load(image)
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (onClickListener != null) {
                        onClickListener.onClick(position, item);
                        Intent intent=null;

                        if ("toStart".equals(fromActivity)){
                            intent = new Intent(v.getContext(), ToStartMovieActivity.class);
                            Log.d("LSY","여기서22");
                        }else{
                            intent = new Intent(v.getContext(), NoteActivity.class);
                            Log.d("LSY", "여기서 11");
                        }
                        intent.putExtra("title", item.getTitle());
                        intent.putExtra("image", item.getImage());
                        v.getContext().startActivity(intent);//onClickListener.onC
                        //((Activity)v.getContext()).finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("LSY", "처리안됨");
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Log.d("LSY", String.valueOf(context));
                String check = String.valueOf(context);
                if (check.contains("ToStartMovieActivity")) {
                    onItemLongClick(holder.getAdapterPosition());
                }
                return true;
            }
        });
    }

    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("알림")
                .setMessage("이 항목을 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String movieTitle = array.get(position).getTitle();
                        String posterUrl = array.get(position).getImage();
                        NoteDBHelper helper = new NoteDBHelper(context);
                        SQLiteDatabase SQLitedb = helper.getReadableDatabase();

                        SQLitedb.execSQL("DELETE FROM tb_memo WHERE title=? AND poster=?", new String[]{movieTitle, posterUrl});
                        array.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

            public void setOnClickListener(OnClickListener onClickListener) {
                this.onClickListener = onClickListener;
            }

            public interface OnClickListener {
                void onClick(int position, MovieItem model);
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
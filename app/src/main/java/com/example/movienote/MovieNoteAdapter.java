package com.example.movienote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieNoteAdapter extends RecyclerView.Adapter<MovieNoteAdapter.MovieNoteViewHolder> {
    ArrayList<Note> noteArrayList;
    private MovieNoteAdapter.OnClickListener onClickListener;

    public MovieNoteAdapter(Context context,ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }

    public void filterList(ArrayList<Note> filteredList) {
        this.noteArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieNoteAdapter.MovieNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_note,parent,false);

        return new MovieNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieNoteAdapter.MovieNoteViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Note note = noteArrayList.get(position);

        holder.movieTitle.setText(note.getMovieTitle());
        holder.note.setText(note.getNote());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (onClickListener != null) {

                        onClickListener.onClick(position, note);
                        Intent intent = new Intent(v.getContext(), ViewNoteFragment.class);
                        //데이터 넘겨주기

                        //intent.putExtra("note",note);
                        //intent.putExtra("title", item.getTitle());
                        //intent.putExtra("image", item.getImage());

                        v.getContext().startActivity(intent);//onClickListener.onC
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("LSY", "처리안됨");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public void setOnClickListener(MovieNoteAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position,Note note);
    }

    public static class MovieNoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView movieTitle,note;
        public MovieNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            note = itemView.findViewById(R.id.note);
        }
    }
}
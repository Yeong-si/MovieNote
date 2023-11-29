package com.example.movienote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieNoteAdapter extends RecyclerView.Adapter<MovieNoteAdapter.MovieNoteViewHolder> {

    Context context;
    ArrayList<Note> noteArrayList;

    public MovieNoteAdapter(Context context, ArrayList<Note> noteArrayList) {
        this.context = context;
        this.noteArrayList = noteArrayList;
    }

    @NonNull
    @Override
    public MovieNoteAdapter.MovieNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_note,parent,false);

        return new MovieNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieNoteAdapter.MovieNoteViewHolder holder, int position) {

        Note note = noteArrayList.get(position);

        holder.note_title.setText(note.getNoteTitle());
        holder.review.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public static class MovieNoteViewHolder extends RecyclerView.ViewHolder{

        TextView note_title,review;

        public MovieNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title = itemView.findViewById(R.id.note_title);
            review = itemView.findViewById(R.id.review);
        }
    }
}
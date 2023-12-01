package com.example.movienote;

import android.content.Context;
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
    public void onBindViewHolder(@NonNull MovieNoteAdapter.MovieNoteViewHolder holder, int position) {

        Note note = noteArrayList.get(position);

        holder.movieTitle.setText(note.getMovieTitle());
        holder.note.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
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
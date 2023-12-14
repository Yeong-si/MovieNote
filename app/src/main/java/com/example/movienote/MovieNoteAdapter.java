package com.example.movienote;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MovieNoteAdapter extends RecyclerView.Adapter<MovieNoteAdapter.MovieNoteViewHolder> {
    ArrayList<Note> noteArrayList;
    private MovieNoteAdapter.OnClickListener onClickListener;
    Context context;
    FirebaseFirestore db;
    CollectionReference collectionReference;

    SharedPreferences shDb;
    SharedPreferences.Editor editor;

    public MovieNoteAdapter(Context context,ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
        this.context = context;
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
                        Intent intent=null;
                        String check = String.valueOf(context);

                        if (check.contains("Finished")){
                            //피니시에서 온경우
                            intent = new Intent(v.getContext(), NoteActivity.class);
                            intent.putExtra("check","modify");
                            intent.putExtra("title",note.getMovieTitle());
                            intent.putExtra("calendar",note.getCalendar());
                            intent.putExtra("rating",note.getRating());
                            intent.putExtra("visible",note.isVisible());
                            intent.putExtra("genre",note.getGenre());
                            //intent.putExtra("image",note.getPoster());
                            intent.putExtra("NoteTitle",note.getNoteTitle());
                            intent.putExtra("comment",note.getComment());
                            intent.putExtra("note",note.getNote());

                            Log.d("LSY","본영화에서 노트클릭");
                            v.getContext().startActivity(intent);

                        }else{
                            //찬민 노트 검색에서 클릭했을 경우
                            //intent = new Intent(v.getContext(), NoteActivity.class);
                            //Log.d("LSY", "여기서 11");
                        }
//                        intent.putExtra("title", item.getTitle());
//                        intent.putExtra("image", item.getImage());
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
                Log.d("LSY", String.valueOf(context));
                String check = String.valueOf(context);
                if (check.contains("Finished")) {
                    onItemLongClick(holder.getAdapterPosition());
                }
                return true;
            }
        });
    }
    //롱클릭 무비노트 삭제 구현
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("알림")
                .setMessage("이 항목을 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db = FirebaseFirestore.getInstance();
                        collectionReference = db.collection("Note");
                        //삭제하려는 것의 목록
                        String movieTitle = noteArrayList.get(position).getMovieTitle();
                        //String posterUrl = noteArrayList.get(position).getImage();

                        Query query = collectionReference.whereEqualTo("comment", noteArrayList.get(position).getComment())
                                .whereEqualTo("noteTitle",noteArrayList.get(position).getNoteTitle())
                                .whereEqualTo("movieTitle",noteArrayList.get(position).getMovieTitle());

                        query.get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                for (DocumentSnapshot document : task.getResult()){
                                    document.getReference().delete();
                                }
                            }else{
                                Log.d("LSY","fail");
                            }
                        });

                        noteArrayList.remove(position);
                        notifyDataSetChanged();

                        shDb = context.getSharedPreferences("FinishedNote",MODE_PRIVATE);
                        editor = shDb.edit();

                        String data1Title = shDb.getString("data1Title", "none");
                        String data1Image = shDb.getString("data1Image", "none");
                        String data2Title = shDb.getString("data2Title","none");
                        String data2Image = shDb.getString("data2Image", "none");

                        if (data1Title.equals(movieTitle)){
                            if (getItemCount() == 0){
                                editor.remove("data1Title");
                                editor.remove("data1Image");
                                editor.commit();
                            } else if (getItemCount() == 1) {
                                editor.putString("data1Title",data2Title);
                                editor.putString("data1Image",data2Image);
                                editor.remove("data2Title");
                                editor.remove("data2Image");
                                editor.commit();
                            }else {
                                //2개 이상 남음 , 이 전 포지션 값 갖고오기
                                editor.putString("data1Title",noteArrayList.get(position-1).getMovieTitle());
                                //포스터 고쳐야해
                                editor.putString("data1Image",noteArrayList.get(position-1).getPoster());
                                editor.commit();
                            }
                        }else if (data2Title.equals(movieTitle)){
                            if (getItemCount() == 1){
                                editor.remove("data2Title");
                                editor.remove("data2Image");
                                editor.commit();
                            }else {
                                editor.putString("data2Title",data1Title);
                                editor.putString("data2Image",data1Image);
                                editor.putString("data1Title",noteArrayList.get(position-2).getMovieTitle());
                                //포스터 고쳐야해
                                editor.putString("data1Image",noteArrayList.get(position-2).getPoster());
                                editor.commit();

                            }
                        }

                    }
                })
                .setNegativeButton("취소", null)
                .show();
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
            /*itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        if (listener != null){

                        }
                    }
                }
            });*/
        }
    }
}
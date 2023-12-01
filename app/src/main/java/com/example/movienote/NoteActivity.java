package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.example.movienote.databinding.ActivityNoteBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NoteActivity extends AppCompatActivity {

    FirebaseUser currentUser;
    CollectionReference noteReference;
    FirebaseFirestore db;

    ActivityNoteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String posterUrl = intent.getStringExtra("image");
        String movieTitle = intent.getStringExtra("title");



        binding.moviename.setText(movieTitle);
        Glide.with(this)
                .load(posterUrl)
                .into(binding.poster);
        //평점 변경
        binding.ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        });

        //캘린더 날짜 선택
        binding.dateBtn.setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dateDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                           binding.date.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        }
                    }, year, month, day);

            dateDialog.show();
        });

        //공개 비공개 버튼
        //추후 공개.비공개 여부로 sns 검색창에 뜰지말지 결정하기.
        //노트가 파이어베이스로 넘어가서 서버에 저장가능하도록 해보기

        binding.openBtn.setOnClickListener(view -> {
            if(binding.openNote.getVisibility() == View.VISIBLE){
                binding.openNote.setVisibility(View.INVISIBLE);
                binding.closedNote.setVisibility(View.VISIBLE);
            }else {
                binding.openNote.setVisibility(View.VISIBLE);
                binding.closedNote.setVisibility(View.INVISIBLE);
            }
        });

        //업로드 후 노트화면으로 가기(업로드부분 짜야함)
        binding.upload.setOnClickListener(new View.OnClickListener() {
            Note note;
            @Override
            public void onClick(View v) {
                boolean visible;

                if (binding.openNote.getVisibility() == View.VISIBLE){
                    visible = true;
                }else {
                    visible =false;
                }
                note = new Note(currentUser.getDisplayName(),binding.moviename.getText().toString(),binding.date.getText().toString(),
                        binding.ratingStar.getRating(),visible,binding.noteTitle.getText().toString(),
                        binding.comment.getText().toString(),binding.note.getText().toString());

                //note.setNoteTitle(binding.moviename1.getText().toString());
                //note.set
                noteReference = FirebaseFirestore.getInstance().collection("Note");
                Map<String, Object> data1 = new HashMap<>();
                data1.put("writer", note.getWriter());
                data1.put("genre", note.getGenre());
                data1.put("movieTitle", note.getMovieTitle());
                data1.put("note", note.getNote());
                data1.put("noteTitle", note.getNoteTitle());
                data1.put("regions", Arrays.asList("west_coast", "norcal"));
                data1.put("comment",note.getComment());
                data1.put("rating",note.getRating());
                data1.put("invisible",note.isVisible());

                noteReference.document(currentUser.getDisplayName()).set(data1);

                startActivity(new Intent(NoteActivity.this, FinishedMovieActivity.class));
            }
        });


    }
    public void addNote(String writer, String movieTitle, String calendar, float rating, boolean visible, String noteTitle, String comment, String note){

    }
        //Note note = new Note();

}
package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RatingBar;

import com.example.movienote.databinding.ActivityNoteBinding;

import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    ActivityNoteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
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
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this, FinishedMovieActivity.class));
            }
        });


    }
}
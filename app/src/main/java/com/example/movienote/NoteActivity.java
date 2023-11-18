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
        binding.ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        });

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

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this, FinishedMovieActivity.class));
            }
        });



    }
}
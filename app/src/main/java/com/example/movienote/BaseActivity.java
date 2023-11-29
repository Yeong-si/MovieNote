// 버튼 4개 달려있는 액티비티

package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movienote.databinding.ActivityBaseBinding;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityBaseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.MovieNoteSearchButton.setOnClickListener(this);
        binding.PartySearchButton.setOnClickListener(this);
        binding.PartyInformationButton.setOnClickListener(this);
        binding.PartyButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // 영화감상노트 검색 버튼
        if (v == binding.MovieNoteSearchButton){
            Intent intent = new Intent(getApplicationContext(), MovieNoteSearchActivity.class);
            startActivity(intent); // 화면 전환
        }
        // 파티 검색 버튼
        else if (v == binding.PartySearchButton) {
            Intent intent = new Intent(getApplicationContext(), PartySearchActivity.class);
            startActivity(intent);
        }
        // 파티 정보 버튼
        else if (v == binding.PartyInformationButton) {
            Intent intent = new Intent(getApplicationContext(), PartyInformationActivity.class);
            startActivity(intent);
        }
        // 파티 만들기 버튼
        else if (v == binding.PartyButton) {
            Intent intent = new Intent(getApplicationContext(), PartyActivity.class);
            startActivity(intent);
        }
    }
}
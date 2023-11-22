// ott를 공유할 파티원들을 구하는 글을 올릴 수 있는 액티비티

package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movienote.databinding.ActivityPartyBinding;

public class PartyActivity extends AppCompatActivity {

    ActivityPartyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
// 사용자가 자신이 속한 파티들을 볼 수 있는 액티비티

package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movienote.databinding.ActivityPartyInformationBinding;

public class PartyInformationActivity extends AppCompatActivity {

    ActivityPartyInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartyInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
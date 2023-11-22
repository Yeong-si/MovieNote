// 공유하고 싶은 ott 서비스의 파티를 검색하는 액티비티

package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movienote.databinding.ActivityPartySearchBinding;

public class PartySearchActivity extends AppCompatActivity {

    ActivityPartySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
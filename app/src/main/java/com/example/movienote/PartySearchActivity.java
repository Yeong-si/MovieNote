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
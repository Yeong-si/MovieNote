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
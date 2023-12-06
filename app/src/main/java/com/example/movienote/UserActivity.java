package com.example.movienote;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movienote.databinding.UserBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserBinding binding = UserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 앱 정보 버튼
        binding.appinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼이 클릭되면 구글 홈페이지로 이동
                openWebPage("https://github.com/Yeong-si/MovieNote");
            }
        });


        Intent intent = getIntent();
        String userProfile = intent.getStringExtra("USER_PROFILE");
        binding.username.setText(userProfile + " 님");
        String content = binding.username.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성
        int start = content.indexOf(userProfile);
        int end = start + content.length() - 1;
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.3f), start, end+1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.username.setText(spannableString);

        binding.logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                finish();
            }
        });

        BottomNavigationView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page_1) {
                    startActivity(new Intent(UserActivity.this, MainActivity.class));
                    return true;
                }

                if (itemId == R.id.page_2) {
                    // Respond to navigation item 2 click
                    startActivity(new Intent(UserActivity.this, PieChartActivity.class));
                    return true;
                }

                if (itemId == R.id.page_3) {
                    // Respond to navigation item 3 click
                    startActivity(new Intent(UserActivity.this, BaseActivity.class));
                    return true;
                }

                if (itemId == R.id.page_4) {
                    // Respond to navigation item 4 click
                    return true;
                }
                return false;
            }
        });
        navigationBarView.getMenu().findItem(R.id.page_4).setChecked(true);

    }

    private void openWebPage(String url) {
        // 웹페이지 열기 위한 인텐트 생성
        Intent intent = new Intent(this, WebPageActivity.class);
        intent.putExtra("WEB_PAGE_URL", url);
        startActivity(intent);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
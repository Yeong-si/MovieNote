package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.movienote.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.startTitle.setText("회원 님이 보고싶은 영화");
        binding.finishTitle.setText("회원 님이 본 영화");

        Log.d("LSY", "데이터 읽기 전");

        NoteDBHelper helper = new NoteDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select title, poster from tb_memo order by _id DESC LIMIT 2 ",null);

        Log.d("LSY", "데이터 읽음");

        /*if (cursor.moveToFirst()) {
            Log.d("LSY", "데이터1 읽음");
            do {
                binding.text1.setText(cursor.getString(0));

                Glide.with(this)
                        .load(cursor.getString(1))
                        .into(binding.image1);
                Log.d("LSY", "데이터2 읽음");

                // 여기에서 커서에서 데이터를 읽어와서 dataList에 추가하는 작업을 수행
                // 예시: dataList.add(new YourDataType(cursor.getString(cursor.getColumnIndex("column_name"))));
            } while (cursor.moveToNext());
            Log.d("LSY", "데이터3 읽음");
            binding.text2.setText(cursor.getString(0));
            Glide.with(this)
                    .load(cursor.getString(1))
                    .into(binding.image2);
        }*/

        if (cursor.moveToFirst()) {
                binding.text1.setText(cursor.getString(0));

                Glide.with(this)
                        .load(cursor.getString(1))
                        .into(binding.image1);
                Log.d("LSY", "데이터2 읽음");

                // 여기에서 커서에서 데이터를 읽어와서 dataList에 추가하는 작업을 수행
                // 예시: dataList.add(new YourDataType(cursor.getString(cursor.getColumnIndex("column_name"))));
            cursor.moveToNext();
            Log.d("LSY", "데이터3 읽음");
            binding.text2.setText(cursor.getString(0));
            Glide.with(this)
                    .load(cursor.getString(1))
                    .into(binding.image2);
        }
        cursor.close();
        db.close();

        /*if (currentUser.getDisplayName() == null){
            binding.startTitle.setText("회원 님이 보고싶은 영화");
            binding.finishTitle.setText("회원 님이 본 영화");
        }else{
            binding.startTitle.setText(currentUser.getDisplayName()+" 님이 보고싶은 영화");
            binding.finishTitle.setText(currentUser.getDisplayName()+" 님이 본 영화");
        }*/

        binding.finishedMoviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FinishedMovieActivity.class));
            }
        });
        binding.startMoive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToStartMovieActivity.class));
            }
        });

        /*binding.tostratMoviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToStartMovieActivity.class));
            }
        });*/

        BottomNavigationView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page_1) {
                    // Respond to navigation item 1 click
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (itemId == R.id.page_2) {
                    // Respond to navigation item 2 click
                    Intent intent = new Intent(MainActivity.this, PieChartActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (itemId == R.id.page_3) {
                    // Respond to navigation item 3 click
                    Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (itemId == R.id.page_4) {
                    // Respond to navigation item 4 click
                    Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });


    }
}
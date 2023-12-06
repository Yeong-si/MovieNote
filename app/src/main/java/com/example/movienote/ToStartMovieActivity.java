package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.movienote.databinding.ActivityToStartMovieBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ToStartMovieActivity extends AppCompatActivity {
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerView;
    List<MovieItem> array;
    MovieAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityToStartMovieBinding binding = ActivityToStartMovieBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.title.setText("회원 님이 보고싶은 영화");
                /*if (currentUser.getDisplayName() == null){
            binding.title.setText("회원 님이 보고싶은 영화");
        }else{
            binding.title.setText(currentUser.getDisplayName()+"님이 보고싶은 영화");
        }*/
        adapter = new MovieAdapter(array, this,"Finished");

        NoteDBHelper helper = new NoteDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select title, poster from tb_memo order by _id DESC",null);

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        array = new ArrayList<MovieItem>();
        adapter = new MovieAdapter(array, this,"Finished");

        recyclerView.setAdapter(adapter);

        array.clear();
        while (cursor.moveToNext()){
            MovieItem temp=new MovieItem();
            temp.setTitle(cursor.getString(0));
            temp.setImage(cursor.getString(1));
            array.add(temp);
        }
        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();


        binding.plusstartmovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToStartMovieActivity.this, MovieSearchActivity.class);
                intent.putExtra("what","toStart");
                startActivity(intent);
            }
        });

        adapter.setOnClickListener(new MovieAdapter.OnClickListener() {
            @Override
            public void onClick(int position, MovieItem model) {
                Log.d("LSY", "클릭 완료");
                // 클릭된 아이템의 정보를 가져와서 NoteActivity로 전환하는 Intent를 생성
                String title = array.get(position).getTitle();
                String image = array.get(position).getImage();
                //Log.d("LSY", title);
                //Log.d("LSY", image);

                Intent intent = new Intent(ToStartMovieActivity.this, NoteActivity.class);
                //intent.putExtra(NEXT_SCREEN,model);
                intent.putExtra("title", title);
                intent.putExtra("image", image);
                intent.putExtra("what", "Finished");

                // NoteActivity 시작
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(ToStartMovieActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
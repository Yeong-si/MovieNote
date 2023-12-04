package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.movienote.databinding.ActivityMovieSearchBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieSearchActivity extends AppCompatActivity {

    Executor executor = Executors.newSingleThreadExecutor();
    ActivityMovieSearchBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<MovieItem> array;
    MovieAdapter adapter;
    final String[] title = new String[1];

    private NoteDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMovieSearchBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerMovie;
        manager = new LinearLayoutManager(this);
        array = new ArrayList<MovieItem>();

        helper = new NoteDBHelper(MovieSearchActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String fromActivity = getIntent().getStringExtra("what");
        if ("toStart".equals(fromActivity)){
            adapter = new MovieAdapter(array, this,"toStart");
        } else if ("Finished".equals(fromActivity)) {
            adapter = new MovieAdapter(array, this,"Finished");
        }


        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = binding.searchBar.getText();
                if (text != null) {
                    title[0] = text.toString();
                    Log.d("LSY", "검색어 받음");
                    executor.execute(() -> {
                        String movies = Movie.MovieSearch(title[0]);
                        runOnUiThread(() -> {
                            if (movies != null) {
                                array.clear();
                                array.addAll(parseJson(movies));
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.e("MovieSearchActivity", "검색 결과가 null입니다.");
                            }
                        });
                    });
                } else {
                    Toast.makeText(MovieSearchActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                    //return;
                }
            }
        });

        adapter.setOnClickListener(new MovieAdapter.OnClickListener() {
            @Override
            public void onClick(int position, MovieItem model) {
                Log.d("LSY", "클릭 완료");


                // 클릭된 아이템의 정보를 가져와서 NoteActivity로 전환하는 Intent를 생성
                String title = array.get(position).getTitle();
                String image = array.get(position).getImage();
                db.execSQL("insert into tb_memo (title,poster) values (?,?)", new String[]{title,image});
                //Log.d("LSY", title);
                //Log.d("LSY", image);

                Intent intent = new Intent(MovieSearchActivity.this, NoteActivity.class);
                //intent.putExtra(NEXT_SCREEN,model);
                intent.putExtra("title", title);
                intent.putExtra("image", image);
                Log.d("LSY", "데이터 넘김");

                // NoteActivity 시작
                startActivity(intent);
            }
        });

    }
    private List<MovieItem> parseJson(String jsonData) {
        List<MovieItem> movies = new ArrayList<>();

        if (jsonData != null) {
            try {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(jsonData).getAsJsonObject();
                JsonArray data = jsonObject.getAsJsonArray("Data");

                JsonObject dataParsing = data.get(0).getAsJsonObject();
                JsonArray result = dataParsing.getAsJsonArray("Result");
                //Log.d("result", String.valueOf(result));

                for (JsonElement element : result) {
                    JsonObject resultObject = element.getAsJsonObject();
                    String title = resultObject.get("title").getAsString();

                    title = title.replace("!HS","");
                    title = title.replace("!HE","");
                    title = title.replace("   ","").replace("  "," ");

                    //Log.d("title",title);
                    String posterUrl = resultObject.get("posters").getAsString();
                    String[] poster = posterUrl.split("\\|");
                    MovieItem movie;
                    if (poster[0].length()==0){
                        String mo = "https://ifh.cc/g/MJ7jPL.png";
                        movie  = new MovieItem(title, mo);
                        Log.d("poster", mo);
                    }else{
                        movie  = new MovieItem(title, poster[0]);
                        Log.d("poster", poster[0]);
                    }

                    movies.add(movie);
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
                Log.e("LSY", "JsonParseException: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LSY", "Exception: " + e.getMessage());
            }}else {
            Log.e("MovieSearchActivity", "JSON data is null");
        }

        return movies;
    }
}
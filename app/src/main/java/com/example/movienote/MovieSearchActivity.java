package com.example.movienote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMovieSearchBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerMovie;
        manager = new LinearLayoutManager(this);
        array = new ArrayList<MovieItem>();
        adapter = new MovieAdapter(array, this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
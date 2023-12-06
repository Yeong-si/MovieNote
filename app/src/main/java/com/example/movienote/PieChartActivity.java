// 장르 개수 세기
//CollectionReference collection = db.collection("Note");
// 노트 컬렉션 가져오기
//Query query = collection.whereEqualto("genre","romance");
// 로맨스인 것만 쿼리로 가져오기
//AggregateQuerySnapshot snapshot = query.aggregate(sum("genre")).get().get();
// 쿼리에서 장르 필드가 있는 합, 79번째 줄처럼 integer로 할 필요 없음, 여기서는 Note 클래스에서 스트링이라 개수일듯
//System.out.println("Sum: " + snapshot.get(sum("genre")));
// 장르 합 반환

package com.example.movienote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.movienote.databinding.ActivityChartBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.Random;

public class PieChartActivity extends AppCompatActivity {

    private ActivityChartBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Executor executor = Executors.newSingleThreadExecutor();

    public static String format_Month = "M";
    Date currentTime = Calendar.getInstance().getTime();

    private int romance = 0, action = 0, animation = 0, sf = 0, horror = 0, drama = 0, comedy = 0;

    private int romanceAll,actionAll,animationAll,sfAll,horrorAll,dramaAll,comedyAll;

    private void fetchDataAndUpdateUI() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("Note");

        // 여러 개의 Firestore 쿼리를 병렬로 실행하고 모든 쿼리가 완료되면 updateUI()를 호출합니다.
        Task<List<QuerySnapshot>> fetchTasks = Tasks.whenAllSuccess(
                fetchGenreCount(collection, "로맨스"),
                fetchGenreCount(collection, "액션"),
                fetchGenreCount(collection, "애니메이션"),
                fetchGenreCount(collection, "드라마"),
                fetchGenreCount(collection, "코미디"),
                fetchGenreCount(collection, "호러"),
                fetchGenreCount(collection, "SF")
        );

        fetchTasks.addOnCompleteListener(this, task -> {
            // 모든 Firestore 쿼리가 완료된 경우에만 UI 업데이트를 수행합니다.
            if (task.isSuccessful()) {
                List<QuerySnapshot> querySnapshots = task.getResult();
                processQueryResults(querySnapshots);
            } else {
                Log.e("Firestore", "Error fetching documents: ", task.getException());
            }
        });
    }

    private Task<QuerySnapshot> fetchGenreCount(CollectionReference collection, String genre) {
        return collection
                .whereEqualTo("genre", genre)
                .whereEqualTo("uid", user.getUid().toString())
                .get();
    }

    private void processQueryResults(List<QuerySnapshot> querySnapshots) {
        int romance = querySnapshots.get(0).size();
        int action = querySnapshots.get(1).size();
        int animation = querySnapshots.get(2).size();
        int drama = querySnapshots.get(3).size();
        int comedy = querySnapshots.get(4).size();
        int horror = querySnapshots.get(5).size();
        int sf = querySnapshots.get(6).size();

        // 결과를 바탕으로 UI 업데이트
        updateUI(romance, action, animation, drama, comedy, horror, sf);
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SimpleDateFormat format = new SimpleDateFormat(format_Month, Locale.getDefault());
        String current = format.format(currentTime);

        binding.stats.setText(current + "월에 감상한 영화");

        // 백분율로 표시하여 파이 차트의 전체 합이 100%가 되도록 보여준다.
        binding.pieChart.setUsePercentValues(true);


        // 넣고 싶은 데이터 설정
        List<PieEntry> dataList = new ArrayList<>();
        dataList.add(new PieEntry(romance, "로맨스"));
        dataList.add(new PieEntry(action, "액션"));
        dataList.add(new PieEntry(animation, "애니메이션"));
        dataList.add(new PieEntry(drama, "드라마"));
        dataList.add(new PieEntry(comedy, "코미디"));
        dataList.add(new PieEntry(horror, "호러"));
        dataList.add(new PieEntry(sf, "SF"));


        // 값에 따른 색상 지정
        List<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(this, R.color.graph1));
        colors.add(ContextCompat.getColor(this, R.color.graph2));
        colors.add(ContextCompat.getColor(this, R.color.graph3));
        colors.add(ContextCompat.getColor(this, R.color.graph4));
        colors.add(ContextCompat.getColor(this, R.color.graph5));
        colors.add(ContextCompat.getColor(this, R.color.graph6));
        colors.add(ContextCompat.getColor(this, R.color.graph7));


        PieDataSet dataSet = new PieDataSet(dataList,"");
        dataSet.setColors(colors);

        // pieChart 안에 들어갈 텍스트 크기
        dataSet.setValueTextSize(16F);

        // pieChart 안에 들어간 value 값 표기 지우기
        dataSet.setDrawValues(false);

        // 데이터 설정 값 삽입
        PieData pieData = new PieData(dataSet);

        binding.pieChart.setData(pieData);
        binding.pieChart.getDescription().setEnabled(false); // 차트 설명 비활성화
        binding.pieChart.getLegend().setEnabled(false); // 하단 설명 비활성화
        binding.pieChart.setRotationEnabled(true); // 차트 회전 활성화
        binding.pieChart.setDrawEntryLabels(false); // 차트 label 비활성
        binding.pieChart.setEntryLabelColor(Color.BLACK); // label 색상
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad); // 1.4초 동안 애니메이션 설정 1400, Easing.EaseInOutQuad
        binding.pieChart.animate();

        // Gson 객체 생성
        Gson gson = new Gson();

        // 원본 데이터를 JSON 형식으로 직렬화
        String jsonString = gson.toJson(dataList);

        // JSON 형식의 데이터를 다시 역직렬화하여 리스트로 변환
        Type typeToken = new TypeToken<List<PieEntry>>() {
        }.getType();
        List<PieEntry> copiedList = gson.fromJson(jsonString, typeToken);

        // 내림차순으로 정렬
        Collections.sort(copiedList, (entry1, entry2) -> Float.compare(entry2.getValue(), entry1.getValue()));

        // 정렬된 데이터 중에서 인덱스 0부터 7까지 추출
        List<PieEntry> selectedDataList = copiedList.subList(0, 7);

        // 텍스트 설정
        binding.genre.setText("주로 " + selectedDataList.get(0).getLabel() + " 영화를 시청하셨군요!");
        binding.bestItem1.setText(selectedDataList.get(0).getLabel());
        binding.bestItem2.setText(selectedDataList.get(1).getLabel());
        binding.bestItem3.setText(selectedDataList.get(2).getLabel());
        binding.bestItem4.setText(selectedDataList.get(3).getLabel());
        binding.bestItem5.setText(selectedDataList.get(4).getLabel());
        binding.bestItem6.setText(selectedDataList.get(5).getLabel());
        binding.bestItem7.setText(selectedDataList.get(6).getLabel());

        // Assuming selectedDataList contains PieEntry objects
        PieEntry selectedEntry1 = selectedDataList.get(0);
        PieEntry selectedEntry2 = selectedDataList.get(1);
        PieEntry selectedEntry3 = selectedDataList.get(2);
        PieEntry selectedEntry4 = selectedDataList.get(3);
        PieEntry selectedEntry5 = selectedDataList.get(4);
        PieEntry selectedEntry6 = selectedDataList.get(5);
        PieEntry selectedEntry7 = selectedDataList.get(6);

        // Create a map to associate labels with colors
        Map<String, Integer> labelColorMap = new HashMap<>();
        labelColorMap.put(dataList.get(0).getLabel(), ContextCompat.getColor(this, R.color.graph1));
        labelColorMap.put(dataList.get(1).getLabel(), ContextCompat.getColor(this, R.color.graph2));
        labelColorMap.put(dataList.get(2).getLabel(), ContextCompat.getColor(this, R.color.graph3));
        labelColorMap.put(dataList.get(3).getLabel(), ContextCompat.getColor(this, R.color.graph4));
        labelColorMap.put(dataList.get(4).getLabel(), ContextCompat.getColor(this, R.color.graph5));
        labelColorMap.put(dataList.get(5).getLabel(), ContextCompat.getColor(this, R.color.graph6));
        labelColorMap.put(dataList.get(6).getLabel(), ContextCompat.getColor(this, R.color.graph7));

        // Set the color of binding.circle based on the label
        String selectedLabel1 = selectedEntry1.getLabel();
        int selectedColor1 = labelColorMap.get(selectedLabel1);
        String selectedLabel2 = selectedEntry2.getLabel();
        int selectedColor2 = labelColorMap.get(selectedLabel2);
        String selectedLabel3 = selectedEntry3.getLabel();
        int selectedColor3 = labelColorMap.get(selectedLabel3);
        String selectedLabel4 = selectedEntry4.getLabel();
        int selectedColor4 = labelColorMap.get(selectedLabel4);
        String selectedLabel5 = selectedEntry5.getLabel();
        int selectedColor5 = labelColorMap.get(selectedLabel5);
        String selectedLabel6 = selectedEntry6.getLabel();
        int selectedColor6 = labelColorMap.get(selectedLabel6);
        String selectedLabel7 = selectedEntry7.getLabel();
        int selectedColor7 = labelColorMap.get(selectedLabel7);

        binding.circle1.setColorFilter(selectedColor1);
        binding.circle2.setColorFilter(selectedColor2);
        binding.circle3.setColorFilter(selectedColor3);
        binding.circle4.setColorFilter(selectedColor4);
        binding.circle5.setColorFilter(selectedColor5);
        binding.circle6.setColorFilter(selectedColor6);
        binding.circle7.setColorFilter(selectedColor7);


        binding.favoritegenre.setText(selectedDataList.get(0).getLabel() + " 장르를 \n선호하시는 회원님께 추천하는 영화");
        String content = binding.favoritegenre.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성
        String word = selectedDataList.get(0).getLabel() ;
        int start = content.indexOf(word);
        int end = start + word.length() + 3;
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F765A3")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.favoritegenre.setText(spannableString);

        String content2 = binding.othermovie.getText().toString();
        SpannableString spannableString2 = new SpannableString(content2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#F765A3")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.othermovie.setText(spannableString2);

        BottomNavigationView navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                    if (itemId == R.id.page_1) {
                        // Respond to navigation item 1 click
                        startActivity(new Intent(PieChartActivity.this, MainActivity.class));
                        return true;
                    }

                if (itemId == R.id.page_2) {
                        return true;
                }

                    if (itemId == R.id.page_3) {
                        // Respond to navigation item 3 click
                        startActivity(new Intent(PieChartActivity.this, BaseActivity.class));
                        return true;
                    }

                if (itemId == R.id.page_4) {
                    // Respond to navigation item 4 click
                        startActivity(new Intent(PieChartActivity.this, GoogleSignInActivity.class));
                        return true;
                }

                return false;
            }
        });
        navigationBarView.getMenu().findItem(R.id.page_2).setChecked(true);

        fetchDataAndUpdateUI();
    }

    private List<String> parseJson(String jsonData) {
        List<String> movies = new LinkedList<>();

        if (jsonData != null) {
            try {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(jsonData).getAsJsonObject();
                JsonArray data = jsonObject.getAsJsonArray("Data");

                if (data.size() > 0) {
                    JsonObject dataParsing = data.get(0).getAsJsonObject();
                    JsonArray result = dataParsing.getAsJsonArray("Result");

                    if (result.size() > 0) {
                        for (JsonElement element : result) {
                            JsonObject resultObject = element.getAsJsonObject();
                            String posterUrl = resultObject.get("posters").getAsString();

                            // Check if posters are available
                            if (!posterUrl.isEmpty()) {
                                String[] poster = posterUrl.split("\\|");

                                if (poster.length > 0 && !poster[0].isEmpty()) {
//                                    Log.d("KDE", "poster url : " + poster[0]);
                                    movies.add(poster[0]);
                                }
                            }
                        }
                    }
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
                Log.e("KDE", "JsonParseException: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("KDE", "favorite poster Exception: " + e.getMessage());
            }
        } else {
            Log.e("MovieSearchActivity", "JSON data is null");
        }

        return movies;
    }

    private String parserankingJson(String jsonData) {
        String movies = "";

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonData).getAsJsonObject();
        JsonArray data = jsonObject.getAsJsonArray("Data");

        JsonObject dataParsing = data.get(0).getAsJsonObject();
        JsonArray result = dataParsing.getAsJsonArray("Result");

        JsonObject resultObject = data.get(0).getAsJsonObject();
        String title = resultObject.get("title").getAsString();

        String posterUrl = resultObject.get("posters").getAsString();
        String[] poster = posterUrl.split("\\|");

        if (poster[0].length()==0){
            String mo = "https://ifh.cc/g/MJ7jPL.png";
            Log.d("poster", mo);
        }else{
            movies = poster[0];
            Log.d("poster", poster[0]);
        }
        return movies;
    }

    private void updateUI(int romance, int action, int animation, int drama, int comedy, int horror, int sf) {
        List<PieEntry> dataList = new ArrayList<>();

        dataList.add(new PieEntry(romance, "로맨스"));
        dataList.add(new PieEntry(action, "액션"));
        dataList.add(new PieEntry(animation, "애니메이션"));
        dataList.add(new PieEntry(drama, "드라마"));
        dataList.add(new PieEntry(comedy, "코미디"));
        dataList.add(new PieEntry(horror, "호러"));
        dataList.add(new PieEntry(sf, "SF"));

        // 값에 따른 색상 지정
        List<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(this, R.color.graph1));
        colors.add(ContextCompat.getColor(this, R.color.graph2));
        colors.add(ContextCompat.getColor(this, R.color.graph3));
        colors.add(ContextCompat.getColor(this, R.color.graph4));
        colors.add(ContextCompat.getColor(this, R.color.graph5));
        colors.add(ContextCompat.getColor(this, R.color.graph6));
        colors.add(ContextCompat.getColor(this, R.color.graph7));


        PieDataSet dataSet = new PieDataSet(dataList, "");
        dataSet.setColors(colors);

        // pieChart 안에 들어갈 텍스트 크기
        dataSet.setValueTextSize(16F);

        // pieChart 안에 들어간 value 값 표기 지우기
        dataSet.setDrawValues(false);

        // 데이터 설정 값 삽입
        PieData pieData = new PieData(dataSet);

        binding.pieChart.setData(pieData);
        binding.pieChart.getDescription().setEnabled(false); // 차트 설명 비활성화
        binding.pieChart.getLegend().setEnabled(false); // 하단 설명 비활성화
        binding.pieChart.setRotationEnabled(true); // 차트 회전 활성화
        binding.pieChart.setDrawEntryLabels(false); // 차트 label 비활성
        binding.pieChart.setEntryLabelColor(Color.BLACK); // label 색상
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad); // 1.4초 동안 애니메이션 설정 1400, Easing.EaseInOutQuad
        binding.pieChart.animate();

        // Gson 객체 생성
        Gson gson = new Gson();

        // 원본 데이터를 JSON 형식으로 직렬화
        String jsonString = gson.toJson(dataList);

        // JSON 형식의 데이터를 다시 역직렬화하여 리스트로 변환
        Type typeToken = new TypeToken<List<PieEntry>>() {
        }.getType();
        List<PieEntry> copiedList = gson.fromJson(jsonString, typeToken);

        // 내림차순으로 정렬
        Collections.sort(copiedList, (entry1, entry2) -> Float.compare(entry2.getValue(), entry1.getValue()));

        // 정렬된 데이터 중에서 인덱스 0부터 7까지 추출
        List<PieEntry> selectedDataList = copiedList.subList(0, 7);

        // 텍스트 설정
        binding.genre.setText("주로 " + selectedDataList.get(0).getLabel() + " 영화를 시청하셨군요!");
        binding.bestItem1.setText(selectedDataList.get(0).getLabel());
        binding.bestItem2.setText(selectedDataList.get(1).getLabel());
        binding.bestItem3.setText(selectedDataList.get(2).getLabel());
        binding.bestItem4.setText(selectedDataList.get(3).getLabel());
        binding.bestItem5.setText(selectedDataList.get(4).getLabel());
        binding.bestItem6.setText(selectedDataList.get(5).getLabel());
        binding.bestItem7.setText(selectedDataList.get(6).getLabel());

        // Assuming selectedDataList contains PieEntry objects
        PieEntry selectedEntry1 = selectedDataList.get(0);
        PieEntry selectedEntry2 = selectedDataList.get(1);
        PieEntry selectedEntry3 = selectedDataList.get(2);
        PieEntry selectedEntry4 = selectedDataList.get(3);
        PieEntry selectedEntry5 = selectedDataList.get(4);
        PieEntry selectedEntry6 = selectedDataList.get(5);
        PieEntry selectedEntry7 = selectedDataList.get(6);

        // Create a map to associate labels with colors
        Map<String, Integer> labelColorMap = new HashMap<>();
        labelColorMap.put(dataList.get(0).getLabel(), ContextCompat.getColor(this, R.color.graph1));
        labelColorMap.put(dataList.get(1).getLabel(), ContextCompat.getColor(this, R.color.graph2));
        labelColorMap.put(dataList.get(2).getLabel(), ContextCompat.getColor(this, R.color.graph3));
        labelColorMap.put(dataList.get(3).getLabel(), ContextCompat.getColor(this, R.color.graph4));
        labelColorMap.put(dataList.get(4).getLabel(), ContextCompat.getColor(this, R.color.graph5));
        labelColorMap.put(dataList.get(5).getLabel(), ContextCompat.getColor(this, R.color.graph6));
        labelColorMap.put(dataList.get(6).getLabel(), ContextCompat.getColor(this, R.color.graph7));

        // Set the color of binding.circle based on the label
        String selectedLabel1 = selectedEntry1.getLabel();
        int selectedColor1 = labelColorMap.get(selectedLabel1);
        String selectedLabel2 = selectedEntry2.getLabel();
        int selectedColor2 = labelColorMap.get(selectedLabel2);
        String selectedLabel3 = selectedEntry3.getLabel();
        int selectedColor3 = labelColorMap.get(selectedLabel3);
        String selectedLabel4 = selectedEntry4.getLabel();
        int selectedColor4 = labelColorMap.get(selectedLabel4);
        String selectedLabel5 = selectedEntry5.getLabel();
        int selectedColor5 = labelColorMap.get(selectedLabel5);
        String selectedLabel6 = selectedEntry6.getLabel();
        int selectedColor6 = labelColorMap.get(selectedLabel6);
        String selectedLabel7 = selectedEntry7.getLabel();
        int selectedColor7 = labelColorMap.get(selectedLabel7);

        binding.circle1.setColorFilter(selectedColor1);
        binding.circle2.setColorFilter(selectedColor2);
        binding.circle3.setColorFilter(selectedColor3);
        binding.circle4.setColorFilter(selectedColor4);
        binding.circle5.setColorFilter(selectedColor5);
        binding.circle6.setColorFilter(selectedColor6);
        binding.circle7.setColorFilter(selectedColor7);


        binding.favoritegenre.setText(selectedDataList.get(0).getLabel() + " 장르를 \n선호하시는 회원님께 추천하는 영화");
        String content = binding.favoritegenre.getText().toString(); //텍스트 가져옴.
        SpannableString spannableString = new SpannableString(content); //객체 생성
        String word = selectedDataList.get(0).getLabel();
        int start = content.indexOf(word);
        int end = start + word.length() + 3;
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F765A3")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.favoritegenre.setText(spannableString);

        String content2 = binding.othermovie.getText().toString();
        SpannableString spannableString2 = new SpannableString(content2);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#F765A3")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.othermovie.setText(spannableString2);

        executor.execute(() -> {
            String movies = Genre.movieSearch(selectedDataList.get(0).getLabel());
            String[] movieRank = Rank.movieRanking();
            String firstMovie = Movie.MovieSearch(movieRank[0]);
            String secondMovie = Movie.MovieSearch(movieRank[1]);
            String thirdMovie = Movie.MovieSearch(movieRank[2]);


            runOnUiThread(() -> {
                if (movies != null) {
                    List<String> posterUrl = parseJson(movies);
                    int min = 0, max = posterUrl.size();
                    int randomNum1 = new Random().nextInt(max - min + 1) + min;
                    int randomNum2 = new Random().nextInt(max - min + 1) + min;
                    int randomNum3 = new Random().nextInt(max - min + 1) + min;

                    Glide.with(this)
                            .load(posterUrl.get(randomNum1))
                            .into(binding.recommend1);
                    Glide.with(this)
                            .load(posterUrl.get(randomNum2))
                            .into(binding.recommend2);
                    Glide.with(this)
                            .load(posterUrl.get(randomNum3))
                            .into(binding.recommend3);
                } else {
                    Log.e("KDE", "장르검색 결과가 null입니다.");
                }
            });
        });

        executor.execute( () -> {
            String[] movieRank = Rank.movieRanking();
            String firstMovie = Movie.MovieSearch(movieRank[0]);
            String secondMovie = Movie.MovieSearch(movieRank[1]);
            String thirdMovie = Movie.MovieSearch(movieRank[2]);
            String posterurl1 = parserankingJson(firstMovie);
        Log.d("KDE", posterurl1);
            String posterurl2 = parserankingJson(secondMovie);
            String posterurl3 = parserankingJson(thirdMovie);

            runOnUiThread(() -> {
                if (movieRank != null) {
                Glide.with(this)
                        .load(posterurl1)
                        .into(binding.other1);
                Glide.with(this)
                        .load(posterurl2)
                        .into(binding.other2);
                Glide.with(this)
                        .load(posterurl3)
                        .into(binding.other3);
                }else {
                    Log.e("KDE", "순위검색 결과가 null입니다.");
                }
            });
        });
    }

}

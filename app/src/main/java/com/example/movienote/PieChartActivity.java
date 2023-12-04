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

import static com.google.firebase.firestore.AggregateField.count;
import static com.google.firebase.firestore.AggregateField.sum;

import android.app.Person;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.movienote.databinding.ActivityChartBinding;
import com.github.mikephil.*;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PieChartActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Executor executor = Executors.newSingleThreadExecutor();

    public static String format_Month = "M";
    Date currentTime = Calendar.getInstance().getTime();
    private FirebaseFirestore db;
    ArrayList<Note> noteArrayList;

    private float romance, action = 0,animation = 0, sf = 0, horror = 0,drama = 0,comedy = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityChartBinding binding = ActivityChartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SimpleDateFormat format = new SimpleDateFormat(format_Month, Locale.getDefault());
        String current = format.format(currentTime);

        binding.stats.setText(current + "월에 감상한 영화");

        // 백분율로 표시하여 파이 차트의 전체 합이 100%가 되도록 보여준다.
        binding.pieChart.setUsePercentValues(true);


        db = FirebaseFirestore.getInstance();

        // 장르 개수 세기
        // 노트 컬렉션 가져오기
        CollectionReference collection = db.collection("Note");
        // 로맨스인 것만 쿼리로 가져오기
        Query query1 = collection.whereEqualTo("genre", "로맨스"); // Ensure the correct string for "로맨스"

        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int romanceCount = task.getResult().size();
                    // Now you have the count of documents with genre "로맨스"
                    Log.d("Firestore", "Romance count: " + romanceCount);

                    // 여기서 변수에 할당하거나 다른 작업 수행
                    romance = romanceCount;
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });

        Query query2 = collection.whereEqualTo("genre", "액션");
        query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int actionCount = task.getResult().size();
                    Log.d("Firestore", "action count: " + actionCount);

                    // 여기서 변수에 할당하거나 다른 작업 수행
                    action = actionCount;
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
        Query query3 = collection.whereEqualTo("genre", "애니메이션");
        query3.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int animationCount = task.getResult().size();
                    Log.d("Firestore", "animation count: " + animationCount);

                    // 여기서 변수에 할당하거나 다른 작업 수행
                    animation = animationCount;
                    Log.d("Firestore", "애니메이션 count: " + animation);
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
        Query query4 = collection.whereEqualTo("genre", "드라마");
        query4.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int dramaCount = task.getResult().size();
                    Log.d("Firestore", "drama count: " + dramaCount);

                    // 여기서 변수에 할당하거나 다른 작업 수행
                    drama = dramaCount;
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
        Query query5 = collection.whereEqualTo("genre", "코미디");
        query5.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int comedyCount = task.getResult().size();
                    Log.d("Firestore", "comedy count: " + comedyCount);

                    // 여기서 변수에 할당하거나 다른 작업 수행
                    comedy = comedyCount;
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
        Query query6 = collection.whereEqualTo("genre", "호러");
        query6.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int horrorCount = task.getResult().size();
                    Log.d("Firestore", "horror count: " + horrorCount);

                    // 여기서 변수에 할당하거나 다른 작업 수행
                    horror = horrorCount;
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });
        Query query7 = collection.whereEqualTo("genre", "SF");
        query7.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int SFCount = task.getResult().size();
                    sf = SFCount;
                    Log.d("Firestore", "SF count: " + SFCount);
                    Log.d("Firestore", "SF count: " + sf);
                } else {
                    Log.e("Firestore", "Error getting documents: ", task.getException());
                }
            }
        });




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
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad); // 1.4초 동안 애니메이션 설정
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

        executor.execute(() -> {
            String movies = Movie.MovieSearch(selectedDataList.get(0).getLabel());
            runOnUiThread(() -> {
                if (movies != null) {
                    String posterUrl = parseJson(movies);
                    Glide.with(this)
                            .load(posterUrl)
                            .into(binding.recommend1);
                } else {
                    Log.e("MovieSearchActivity", "검색 결과가 null입니다.");
                }
            });
        });

//        BottomNavigationView navigationBarView = findViewById(R.id.bottom_navigation);
//        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int itemId = item.getItemId();
//
//                    if (itemId == R.id.page_1) {
//                        // Respond to navigation item 1 click
//                        Intent intent = new Intent(PieChartActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        return true;
//                    }
//
//                if (itemId == R.id.page_2) {
//                    // Respond to navigation item 2 click
//                    Intent intent = new Intent(PieChartActivity.this, PieChartActivity.class);
//                    startActivity(intent);
//                    return true;
//                }
//
//                    if (itemId == R.id.page_3) {
//                        // Respond to navigation item 3 click
//                        Intent intent = new Intent(PieChartActivity.this, BaseActivity.class);
//                        startActivity(intent);
//                        return true;
//                    }
//
//                if (itemId == R.id.page_4) {
//                    // Respond to navigation item 4 click
//                    Intent intent = new Intent(PieChartActivity.this, GoogleSignInActivity.class);
//                    startActivity(intent);
//                    return true;
//                }
//
//                return false;
//            }
//        });


    }

    private void fetchUserNotes(String userUid) {
        db.collection("Note")
                .whereEqualTo("uid", userUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            noteArrayList = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                Note note = document.toObject(Note.class);
                                if (note != null) {
                                    noteArrayList.add(note);
                                }
                            }

                            // Calculate genre-based statistics for the user's notes
                            calculateUserStatistics();

                        } else {
                            Exception exception = task.getException();
                            if (exception != null) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });
    }


    private void calculateUserStatistics() {
        for (Note note : noteArrayList) {
            String genre = note.getGenre(); // Replace with the actual method to get the genre from your Note object

            // Increment the count based on the genre
            switch (genre) {
                case "로맨스":
                    romance++;
                    break;
                case "액션":
                    action++;
                    break;
                case "애니메이션":
                    animation++;
                    break;
                case "SF":
                    sf++;
                    break;
                case "호러":
                    horror++;
                    break;
                case "드라마":
                    drama++;
                    break;
                case "코미디":
                    comedy++;
                    break;
                // Add more cases for other genres if needed
            }
        }
    }

    private String parseJson(String jsonData) {
        String movies = "";

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

                    //Log.d("title",title);
                    String posterUrl = resultObject.get("posters").getAsString();
                    String[] poster = posterUrl.split("\\|");
                    MovieItem movie;
                    if (poster[0].length()==0){
                        return "https://ifh.cc/g/MJ7jPL.png";
                    }else{
                        return poster[0];
                    }

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

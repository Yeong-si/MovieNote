// 다른 사람들이 쓴 영화감상노트를 검색해서 볼 수 있는 액티비티

package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.movienote.databinding.ActivityMovieNoteSearchBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MovieNoteSearchActivity extends AppCompatActivity {

    ActivityMovieNoteSearchBinding binding;
    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<Note> noteArrayList;
    MovieNoteAdapter movieNoteAdapter;
    FirebaseFirestore db;
    NavigationBarView navigationBarView;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMovieNoteSearchBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page_1) {
                    startActivity(new Intent(MovieNoteSearchActivity.this, MainActivity.class));
                    return true;
                }

                if (itemId == R.id.page_2) {
                    // Respond to navigation item 2 click
                    startActivity(new Intent(MovieNoteSearchActivity.this, PieChartActivity.class));
                    return true;
                }

                if (itemId == R.id.page_3) {
                    // Respond to navigation item 3 click
                    startActivity(new Intent(MovieNoteSearchActivity.this, BaseActivity.class));
                    return true;
                }

                if (itemId == R.id.page_4) {
                    // Respond to navigation item 4 click
                    startActivity(new Intent(MovieNoteSearchActivity.this, GoogleSignInActivity.class));
                    return true;
                }
                return false;
            }
        });
        navigationBarView.getMenu().findItem(R.id.page_3).setChecked(true);


        recyclerView = binding.movieNoteSearchRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        noteArrayList = new ArrayList<Note>();
        movieNoteAdapter = new MovieNoteAdapter(MovieNoteSearchActivity.this,noteArrayList);

        recyclerView.setAdapter(movieNoteAdapter);

        EventChangeListener();

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.searchBar.getText().toString();

                db.collection("Note")
                        .whereEqualTo("writer", currentUser)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                if (error != null) {
                                    Log.e("FireStore error",error.getMessage());
                                    return;
                                }

                                for(DocumentChange dc: value.getDocumentChanges()) {

                                    if (dc.getType() == DocumentChange.Type.ADDED) {
                                        noteArrayList.add(dc.getDocument().toObject(Note.class));
                                    }
                                }

                                ArrayList<Note> tmp = new ArrayList<>();

                                for (Note output : noteArrayList){
                                    //노트가 공개이고, comment에 text관련이 쓰여있다면
                                    if (output.isVisible() == false && output.getMovieTitle().contains(text)){
                                        tmp.add(output);
                                    }
                                }

                                noteArrayList.clear();
                                noteArrayList.addAll(tmp);
                                movieNoteAdapter.notifyDataSetChanged();
                            }
                        });
            }
        });

        movieNoteAdapter.setOnClickListener(new MovieNoteAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Note model) {
                Log.d("LSY", "클릭 완료");
                // 클릭된 아이템의 정보를 가져와서 NoteActivity로 전환하는 Intent를 생성
                //이런식으로 리스트에서 포지션에 맞는 각각을 들고오는 것 구현하면 돼
                String title = noteArrayList.get(position).getMovieTitle();

                //Log.d("LSY", title);
                //Log.d("LSY", image);

                Intent intent = new Intent(MovieNoteSearchActivity.this, ViewNoteFragment.class);
                //intent.putExtra(NEXT_SCREEN,model);
                intent.putExtra("title", title);
                //intent.putExtra("image", image);
                Log.d("LSY", "데이터 넘김");

                // NoteActivity 시작
                startActivity(intent);
            }
        });
    }

    private void EventChangeListener() {

        db.collection("Note")
                .whereEqualTo("invisible", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            Log.e("FireStore error",error.getMessage());
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                noteArrayList.add(dc.getDocument().toObject(Note.class));
                            }
                            movieNoteAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}
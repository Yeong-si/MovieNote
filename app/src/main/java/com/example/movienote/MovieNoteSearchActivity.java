// 다른 사람들이 쓴 영화감상노트를 검색해서 볼 수 있는 액티비티

package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
    ProgressDialog progressDialog;
    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMovieNoteSearchBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

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


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = binding.movieNoteSearchRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        noteArrayList = new ArrayList<Note>();
        movieNoteAdapter = new MovieNoteAdapter(MovieNoteSearchActivity.this,noteArrayList);

        recyclerView.setAdapter(movieNoteAdapter);

//        binding.noteSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventChangeListener();
//                String text = binding.noteSearchBar.getText().toString();
//                int i=0;
//                for (Note output : noteArrayList){
//                    //노트가 공개이고, comment에 text관련이 쓰여있다면
//                    if (output.isVisible() == true && output.getNote().contains(text)){
//                        noteArrayList.set(i++,output);
//                    }
//                }
//                movieNoteAdapter.notifyDataSetChanged();
//            }
//        });

//        movieNoteAdapter.setOnClickListener(new MovieNoteAdapter.OnClickListener() {
//            @Override
//            public void onClick(int position, Note model) {
//                Log.d("LSY", "클릭 완료");
//                // 클릭된 아이템의 정보를 가져와서 NoteActivity로 전환하는 Intent를 생성
//                //이런식으로 리스트에서 포지션에 맞는 각각을 들고오는 것 구현하면 돼
//                String title = noteArrayList.get(position).getMovieTitle();
//
//                //Log.d("LSY", title);
//                //Log.d("LSY", image);
//
//                Intent intent = new Intent(MovieNoteSearchActivity.this, ViewNoteFragment.class);
//                //intent.putExtra(NEXT_SCREEN,model);
//                intent.putExtra("title", title);
//                //intent.putExtra("image", image);
//                Log.d("LSY", "데이터 넘김");
//
//                // NoteActivity 시작
//                startActivity(intent);
//            }
//        });

//        EventChangeListener();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // below line is to get our inflater
//        MenuInflater inflater = getMenuInflater();
//
//        // inside inflater we are inflating our menu file.
//        inflater.inflate(R.menu.search_menu, menu);
//
//        // below line is to get our menu item.
//        MenuItem searchItem = menu.findItem(R.id.actionSearch);
//
//        // getting search view of our item.
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        // below line is to call set on query text listener method.
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // inside on query text change method we are
//                // calling a method to filter our recycler view.
//                filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }


//    private void filter(String text) {
//        // creating a new array list to filter our data.
//        ArrayList<Note> filteredlist = new ArrayList<Note>();
//
//        // running a for loop to compare elements.
//        for (Note item : noteArrayList) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (item.getNote().toLowerCase().contains(text.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredlist.add(item);
//            }
//        }
//        if (filteredlist.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
//            movieNoteAdapter.filterList(filteredlist);
//        }
//    }

//    private void EventChangeListener() {
//
//        db.collection("Note").orderBy("note", Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                        if (error != null) {
//
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//
//                            Log.e("FireStore error", error.getMessage());
//                            return;
//                        }
//
//                        for (DocumentChange dc : value.getDocumentChanges()) {
//
//                            if (dc.getType() == DocumentChange.Type.ADDED) {
//                                noteArrayList.add(dc.getDocument().toObject(Note.class));
//                            }
//
//                            movieNoteAdapter.notifyDataSetChanged();
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//                        }
//                    }
//                });
//    }
}
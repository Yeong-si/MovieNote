// 다른 사람들이 쓴 영화감상노트를 검색해서 볼 수 있는 액티비티

package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.movienote.databinding.ActivityMovieNoteSearchBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MovieNoteSearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<Note> noteArrayList;
    MovieNoteAdapter movieNoteAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_note_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.movieNoteSearchRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        noteArrayList = new ArrayList<Note>();
        movieNoteAdapter = new MovieNoteAdapter(MovieNoteSearchActivity.this,noteArrayList);

        recyclerView.setAdapter(movieNoteAdapter);

        EventChangeListener();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        getMenuInflater().inflate(R.menu.search_menu,menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<Note> filteredlist = new ArrayList<Note>();

        // running a for loop to compare elements.
        for (Note item : noteArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getNote().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            movieNoteAdapter.filterList(filteredlist);
        }
    }

    private void EventChangeListener() {

        db.collection("Note").orderBy("note", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Log.e("FireStore error",error.getMessage());
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                noteArrayList.add(dc.getDocument().toObject(Note.class));
                            }

                            movieNoteAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}
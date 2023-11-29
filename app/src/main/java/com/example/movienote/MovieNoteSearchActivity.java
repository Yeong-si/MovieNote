// 다른 사람들이 쓴 영화감상노트를 검색해서 볼 수 있는 액티비티

package com.example.movienote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.movienote.databinding.ActivityMovieNoteSearchBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MovieNoteSearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Note> noteArrayList;
    MovieNoteAdapter movieNoteAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    ActivityMovieNoteSearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieNoteSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        EventChangeListener();
    }

    private void EventChangeListener() {

        db.collection("Note").orderBy("movieTitle", Query.Direction.ASCENDING)
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
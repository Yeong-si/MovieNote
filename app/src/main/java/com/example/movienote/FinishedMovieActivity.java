package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.movienote.databinding.ActivityFinishedMovieBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class FinishedMovieActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Note> noteArrayList;
    MovieNoteAdapter movieNoteAdapter;
    FirebaseFirestore db;
    FirebaseUser user;

//    SharedPreferences shDb;
//    SharedPreferences.Editor editor;

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(FinishedMovieActivity.this, MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityFinishedMovieBinding binding = ActivityFinishedMovieBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        user = FirebaseAuth.getInstance().getCurrentUser();

        binding.title.setText("회원 님이 본 영화");

        /*if (user.getDisplayName() == null){
            binding.title.setText("회원 님이 본 영화");
        }else{
            binding.title.setText(user.getDisplayName()+"님이 본 영화");
        }*/

        binding.plusmovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishedMovieActivity.this, MovieSearchActivity.class);
                intent.putExtra("what","Finished");
                startActivity(intent);
                finish();
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        db.collection("Note")
                .whereEqualTo("uid", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId() + " => " + document.getData());
                                //noteArrayList.add(document.getData().get(""));
                            }
                        } else {
                            Log.d("", "Error getting documents: ", task.getException());
                        }
                    }
                });

        noteArrayList = new ArrayList<Note>();
        movieNoteAdapter = new MovieNoteAdapter(FinishedMovieActivity.this,noteArrayList);

        recyclerView.setAdapter(movieNoteAdapter);

        EventChangeListener();

    }


    private void EventChangeListener() {

        //db.collection("Note").orderBy("note", Query.Direction.ASCENDING)
        db.collection("Note").whereEqualTo("uid",user.getUid())
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
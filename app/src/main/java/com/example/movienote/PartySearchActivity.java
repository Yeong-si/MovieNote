// 공유하고 싶은 ott 서비스의 파티를 검색하는 액티비티

package com.example.movienote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.movienote.databinding.ActivityPartySearchBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PartySearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Party> partyArrayList;
    PartySearchAdapter partySearchAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    ActivityPartySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = binding.partySearchRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        partyArrayList = new ArrayList<Party>();
        partySearchAdapter = new PartySearchAdapter(PartySearchActivity.this,partyArrayList);

        recyclerView.setAdapter(partySearchAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {

        db.collection("Party").orderBy("subscription", Query.Direction.ASCENDING)
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
                                partyArrayList.add(dc.getDocument().toObject(Party.class));
                            }

                            partySearchAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }
}
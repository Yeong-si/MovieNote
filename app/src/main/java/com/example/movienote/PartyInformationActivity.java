// 사용자가 자신이 속한 파티들을 볼 수 있는 액티비티

package com.example.movienote;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienote.databinding.ActivityPartyInformationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class PartyInformationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Party> partyArrayList;
    ArrayList<Member> memberArrayList;
    PartyInformationAdapter partyInformationAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    ActivityPartyInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartyInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = binding.partyInformationRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        partyArrayList = new ArrayList<Party>();
        partyInformationAdapter = new PartyInformationAdapter(getApplicationContext(),partyArrayList,memberArrayList);

        recyclerView.setAdapter(partyInformationAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Member")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> fields = document.getData();
                                for (Map.Entry<String, Object> field : fields.entrySet()) {
                                    if ("valueToFind".equals(field.getValue())) {
                                        Log.d("TAG", "Match found in document with ID: " + document.getId() + ", Field: " + field.getKey());
                                        // 이제 document를 사용하여 필요한 작업을 수행할 수 있습니다.
                                        db.collection("Member").whereEqualTo(field.getKey(),currentUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot documentA : task.getResult()) {
                                                        // B 컬렉션에서 ID가 일치하는 문서를 찾습니다.
                                                        db.collection("Party").document(documentA.getId())
                                                                .get()
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentB) {
                                                                        if (documentB.exists()) {
                                                                            partyArrayList.add(documentB.toObject(Party.class));
                                                                            partyInformationAdapter.notifyDataSetChanged();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                } else {
                                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

//        db.collection("Party").orderBy("subscription", Query.Direction.ASCENDING)
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
//                            Log.e("FireStore error",error.getMessage());
//                            return;
//                        }
//
//                        for(DocumentChange dc: value.getDocumentChanges()) {
//
//                            if (dc.getType() == DocumentChange.Type.ADDED) {
//                                partyArrayList.add(dc.getDocument().toObject(Party.class));
//                            }
//
//                            partyInformationAdapter.notifyDataSetChanged();
//                            if (progressDialog.isShowing()) {
//                                progressDialog.dismiss();
//                            }
//                        }
//                    }
//                });
    }
}
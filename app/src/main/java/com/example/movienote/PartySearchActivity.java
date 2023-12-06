// 공유하고 싶은 ott 서비스의 파티를 검색하는 액티비티

package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.movienote.databinding.ActivityPartySearchBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PartySearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Party> partyArrayList;
    PartySearchAdapter partySearchAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String selectedSubscription;

    ActivityPartySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedSubscription = "default";

        binding.subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = { "넷플릭스 프리미엄", "티빙 프리미엄", "라프텔 프리미엄", "웨이브 프리미엄","왓챠 프리미엄","디즈니+ 프리미엄" };
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        PartySearchActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("구독할 상품 선택");
                alertDialogBuilder.setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                selectedSubscription = items[id].toString();
                                // 프로그램을 종료한다
                                Toast.makeText(getApplicationContext(),
                                        items[id] + " 선택했습니다.",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                // 다이얼로그 생성
                AlertDialog alertDialog2 = alertDialogBuilder.create();

                // 다이얼로그 보여주기
                alertDialog2.show();
            }
        });

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

        db.collection("Party").whereEqualTo("subscription",selectedSubscription)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String field = document.getString("subscription");

                                Task<DocumentSnapshot> subscription = db.collection("Subscription").document(field)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    // 여기서 documentSnapshot은 'Collection1'의 문서입니다.
                                                    // 이 문서의 필드 값을 가져오거나 다른 작업을 수행할 수 있습니다.
                                                    ArrayList<String> members = (ArrayList<String>) documentSnapshot.get("member");
                                                    if (members.size() < Integer.parseInt(documentSnapshot.getString("max"))) {
                                                        // members.size()가 ott의 동시 시청 가능한 사람보다 작을 때만 데이터를 처리합니다.
                                                        // 이 부분에서 리사이클러뷰 어댑터에 데이터를 추가하고, 어댑터를 업데이트합니다.
                                                        partyArrayList.add(documentSnapshot.toObject(Party.class));
                                                    }
                                                    partySearchAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.d("FireStore", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
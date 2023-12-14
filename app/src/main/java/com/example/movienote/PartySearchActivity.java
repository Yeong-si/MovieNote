// 공유하고 싶은 ott 서비스의 파티를 검색하는 액티비티

package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.movienote.databinding.ActivityPartySearchBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
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
import java.util.List;

public class PartySearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Party> partyArrayList;
    PartySearchAdapter partySearchAdapter;
    FirebaseFirestore db;
    String selectedSubscription;
    NavigationBarView navigationBarView;

    ActivityPartySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedSubscription = "default";

        navigationBarView = findViewById(R.id.bottom_navigation);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.page_1) {
                    startActivity(new Intent(PartySearchActivity.this, MainActivity.class));
                    return true;
                }

                if (itemId == R.id.page_2) {
                    // Respond to navigation item 2 click
                    startActivity(new Intent(PartySearchActivity.this, PieChartActivity.class));
                    return true;
                }

                if (itemId == R.id.page_3) {
                    // Respond to navigation item 3 click
                    startActivity(new Intent(PartySearchActivity.this, BaseActivity.class));
                    return true;
                }

                if (itemId == R.id.page_4) {
                    // Respond to navigation item 4 click
                    startActivity(new Intent(PartySearchActivity.this, GoogleSignInActivity.class));
                    return true;
                }
                return false;
            }
        });
        navigationBarView.getMenu().findItem(R.id.page_3).setChecked(true);


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


        db.collection("Party").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() ) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // DocumentSnapshot에서 리스트 타입의 필드 가져오기
                        Object memberField = document.get("member");

                        // 만약 필드가 리스트 타입이라면
                        if (memberField instanceof List) {
                            List<String> member = (List<String>) memberField;
                            if(member.size() < 4){
                                partyArrayList.add(document.toObject(Party.class));
                            }
                        }
                        partySearchAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
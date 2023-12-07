// ott를 공유할 파티원들을 구하는 글을 올릴 수 있는 액티비티

package com.example.movienote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienote.databinding.ActivityPartyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.ServerTimestamps;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.grpc.Server;

public class PartyActivity extends AppCompatActivity {

    ActivityPartyBinding binding;
    FirebaseFirestore db;
    Map<String,Object> data;
    CollectionReference partyReference;
//    CollectionReference memberReference;

    List<String> member;
    CollectionReference subscriptionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();

        partyReference = db.collection("Party");
        subscriptionReference = db.collection("Subscription");
//        memberReference = db.collection("Member");

        data = new HashMap<String,Object>();
        binding.subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = { "넷플릭스 프리미엄", "티빙 프리미엄", "라프텔 프리미엄", "웨이브 프리미엄","왓챠 프리미엄","디즈니+ 프리미엄" };
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        PartyActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("구독할 상품 선택");
                alertDialogBuilder.setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                binding.subscription.setText(items[id].toString());
                                data.put("subscription",items[id].toString());
                                subscriptionReference.document(items[id].toString())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document != null) {
                                                data.put("price",Integer.toString(
                                                        Integer.parseInt(document.getString("price"))
                                                        /Integer.parseInt(document.getString("max"))
                                                        )
                                                );
                                                Log.i("LOGGER","success putting price field");
                                            } else {
                                                Log.d("LOGGER", "No such document");
                                            }
                                        } else {
                                            Log.d("LOGGER", "get failed with ", task.getException());
                                        }
                                    }
                                });

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

        binding.id.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    data.put("id",binding.id.getText());
                    return true;
                }
                return false;
            }
        });
        binding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    data.put("password",binding.password.getText());
                    return true;
                }
                return false;
            }
        });
        binding.accountHolderName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    data.put("accountHolderName",binding.accountHolderName.getText());
                    return true;
                }
                return false;
            }
        });
        binding.bankName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    data.put("bankName",binding.bankName.getText());
                    return true;
                }
                return false;
            }
        });
        binding.accountNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    data.put("accountNumber",binding.accountNumber.getText());
                    return true;
                }
                return false;
            }
        });
        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.containsKey("id") & data.containsKey("password")&data.containsKey("accountHolderName") &data.containsKey("bankName")
                        &data.containsKey("accountNumber")&data.containsKey("subscription") &data.containsKey("price")){
                    LocalDateTime dateTime = LocalDateTime.now();
//                    Member member = new Member();
//                    member.setMember1(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    member.setMember_size(Integer.toString(1));

                    member = new ArrayList<String>();
                    member.add(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    Party party = new Party(member,data.get("subscription").toString(),data.get("accountNumber").toString()
                            ,data.get("bankName").toString(),data.get("accountHolderName").toString()
                            ,data.get("price").toString(),data.get("id").toString(),data.get("password").toString());

//                    memberReference.document(dateTime.toString()).set(member);
                    partyReference.document(dateTime.toString()).set(party);

                    Intent intent = new Intent(getApplicationContext(), PartyInformationActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "양식을 완성해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
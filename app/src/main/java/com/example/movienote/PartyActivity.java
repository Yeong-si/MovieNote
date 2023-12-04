// ott를 공유할 파티원들을 구하는 글을 올릴 수 있는 액티비티

package com.example.movienote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.movienote.databinding.ActivityPartyBinding;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PartyActivity extends AppCompatActivity {

    ActivityPartyBinding binding;
    FirebaseFirestore db;
    Map<String,Object> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();

        CollectionReference party = db.collection("Party");

        data = new HashMap<>();
        binding.subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = { "넷플릭스", "티빙", "라프텔 프리미엄", "웨이브" };
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        PartyActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("구독할 상품 선택");
                alertDialogBuilder.setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                data.put("subscription",items[id].toString());

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
        binding.id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("id",binding.id.getText().toString());
            }
        });
        binding.password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("password",binding.password.getText().toString());
            }
        });
        binding.accountHolderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("accountHolderName",binding.accountHolderName.getText().toString());
            }
        });
        binding.bankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("bankName",binding.bankName.getText().toString());
            }
        });
        binding.accountNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("accountNumber",binding.accountNumber.getText().toString());
            }
        });
        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put("timestamp", FieldValue.serverTimestamp());
                party.document(FieldValue.serverTimestamp().toString()).set(data);
            }
        });
    }
}
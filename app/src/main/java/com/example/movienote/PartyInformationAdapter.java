package com.example.movienote;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PartyInformationAdapter extends RecyclerView.Adapter<PartyInformationAdapter.PartyInformationViewHolder> {

    Context context;
    ArrayList<Party> partyArrayList;
    ArrayList<Member> memberArrayList;

//    public PartyInformationAdapter(Context context,ArrayList<Party> partyArrayList,ArrayList<Member> memberArrayList) {
//        this.context = context;
//        this.partyArrayList = partyArrayList;
//        this.memberArrayList = memberArrayList;
//    }

    public PartyInformationAdapter(Context context, ArrayList<Party> partyArrayList) {
        this.context = context;
        this.partyArrayList = partyArrayList;
    }

    @NonNull
    @Override
    public PartyInformationAdapter.PartyInformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_party_information,parent,false);

        return new PartyInformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyInformationAdapter.PartyInformationViewHolder holder, int position) {

        Party party = partyArrayList.get(position);


        FirebaseFirestore.getInstance().collection("Subscription")
                .document(party.getSubscription()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String fieldValue = document.get("max").toString();
                                // 이제 'fieldValue' 변수에는 필드의 값이 들어 있습니다.

                                if (party.getMember().size() < Integer.parseInt(fieldValue)){
                                    holder.id.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(context, "아직 파티원이 다 모이지 않았습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.password.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(context, "아직 파티원이 다 모이지 않았습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.accountNumber.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(context, "아직 파티원이 다 모이지 않았습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.accountHolderName.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(context, "아직 파티원이 다 모이지 않았습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.price.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(context, "아직 파티원이 다 모이지 않았습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    holder.bankName.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(context, "아직 파티원이 다 모이지 않았습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                else {
                                    holder.id.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 원래 버튼 텍스트 저장
                                            String originalText = holder.id.getText().toString();

                                            // 클립보드에 복사할 텍스트
                                            String temporaryText = party.getId();

                                            // 클립보드에 텍스트 복사
                                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("label", temporaryText);
                                            clipboard.setPrimaryClip(clip);

                                            // 원하는 텍스트로 버튼 텍스트 변경
                                            holder.id.setText(temporaryText);

                                            // 일정 시간 후에 버튼 텍스트를 원래대로 변경
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.id.setText(originalText);
                                                }
                                            }, 3000);  // 3000 밀리초(3초) 후에 실행
                                        }
                                    });
                                    holder.password.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 원래 버튼 텍스트 저장
                                            String originalText = holder.password.getText().toString();

                                            // 클립보드에 복사할 텍스트
                                            String temporaryText = party.getPassword();

                                            // 클립보드에 텍스트 복사
                                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("label", temporaryText);
                                            clipboard.setPrimaryClip(clip);

                                            // 원하는 텍스트로 버튼 텍스트 변경
                                            holder.password.setText(temporaryText);

                                            // 일정 시간 후에 버튼 텍스트를 원래대로 변경
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.password.setText(originalText);
                                                }
                                            }, 3000);  // 3000 밀리초(3초) 후에 실행
                                        }
                                    });
                                    holder.accountNumber.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 원래 버튼 텍스트 저장
                                            String originalText = holder.accountNumber.getText().toString();

                                            // 클립보드에 복사할 텍스트
                                            String temporaryText = party.getAccountNumber().toString();

                                            // 클립보드에 텍스트 복사
                                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("label", temporaryText);
                                            clipboard.setPrimaryClip(clip);

                                            // 원하는 텍스트로 버튼 텍스트 변경
                                            holder.accountNumber.setText(temporaryText);

                                            // 일정 시간 후에 버튼 텍스트를 원래대로 변경
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.accountNumber.setText(originalText);
                                                }
                                            }, 3000);  // 3000 밀리초(3초) 후에 실행
                                        }
                                    });
                                    holder.accountHolderName.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 원래 버튼 텍스트 저장
                                            String originalText = holder.accountHolderName.getText().toString();

                                            // 클립보드에 복사할 텍스트
                                            String temporaryText = party.getAccountHolderName();

                                            // 클립보드에 텍스트 복사
                                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("label", temporaryText);
                                            clipboard.setPrimaryClip(clip);

                                            // 원하는 텍스트로 버튼 텍스트 변경
                                            holder.accountHolderName.setText(temporaryText);

                                            // 일정 시간 후에 버튼 텍스트를 원래대로 변경
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.accountHolderName.setText(originalText);
                                                }
                                            }, 3000);  // 3000 밀리초(3초) 후에 실행
                                        }
                                    });
                                    holder.price.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 원래 버튼 텍스트 저장
                                            String originalText = holder.price.getText().toString();

                                            // 클립보드에 복사할 텍스트
                                            String temporaryText = party.getPrice().toString();

                                            // 클립보드에 텍스트 복사
                                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("label", temporaryText);
                                            clipboard.setPrimaryClip(clip);

                                            // 원하는 텍스트로 버튼 텍스트 변경
                                            holder.price.setText(temporaryText);

                                            // 일정 시간 후에 버튼 텍스트를 원래대로 변경
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.price.setText(originalText);
                                                }
                                            }, 3000);  // 3000 밀리초(3초) 후에 실행
                                        }
                                    });
                                    holder.bankName.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 원래 버튼 텍스트 저장
                                            String originalText = holder.bankName.getText().toString();

                                            // 클립보드에 복사할 텍스트
                                            String temporaryText = party.getBankName();

                                            // 클립보드에 텍스트 복사
                                            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("label", temporaryText);
                                            clipboard.setPrimaryClip(clip);

                                            // 원하는 텍스트로 버튼 텍스트 변경
                                            holder.bankName.setText(temporaryText);

                                            // 일정 시간 후에 버튼 텍스트를 원래대로 변경
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    holder.bankName.setText(originalText);
                                                }
                                            }, 3000);  // 3000 밀리초(3초) 후에 실행
                                        }
                                    });
                                }

                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });


        switch (party.getSubscription()) {
            case "넷플릭스 프리미엄" :
                holder.ott_icon.setImageResource(R.drawable.netflix);
                break;
            case "디즈니+ 프리미엄" :
                holder.ott_icon.setImageResource(R.drawable.disney);
                break;
            case "티빙 프리미엄" :
                holder.ott_icon.setImageResource(R.drawable.tving);
                break;
            case "웨이브 프리미엄" :
                holder.ott_icon.setImageResource(R.drawable.wavve);
                break;
            case "왓챠 프리미엄" :
                holder.ott_icon.setImageResource(R.drawable.watcha);
                break;
            case "라프텔 프리미엄" :
                holder.ott_icon.setImageResource(R.drawable.laftel);
                break;
            default:
                holder.ott_icon.setImageResource(R.drawable.ott_icon);
                break;
        }


//        FirebaseFirestore.getInstance().collection("Party")
//                .whereArrayContains("member", FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                        if (error != null) {
//                            Log.w("TAG", "Listen failed.", error);
//                            return;
//                        }
//
//                        for (QueryDocumentSnapshot doc : value) {
//                            if (doc.get("yourArrayField") != null) {
//                                Log.d("TAG", "Data: " + doc.getData());
//                                List<String> member = (List<String>) doc.get("member");
//                                for (int i=0;i < member.size();i++){
//                                    TextView textView = new TextView(context);
//                                    textView.setText(member.get(i));
//                                    textView.setTextColor(Color.BLUE);
//                                    textView.setWidth();
//
//                                    textView.setTextSize(20);
//                                    holder.gridLayout.addView(textView);
//                                    Log.d("PartyInformationAdapter","gridLayout");
//                                }
//                            }
//                        }
//                    }
//                });


        holder.subscription.setText(party.getSubscription());
// member 컬렉션을 사용할때
//        FirebaseFirestore.getInstance()
//                .collection("Member")
//                .document(party.getId())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        // 문서의 필드 값을 가져옵니다.
//                        TextView textView1 = new TextView(context);
//                        textView1.setText(document.getString("member1"));
//                        holder.gridLayout.addView(textView1);
//
//                        TextView textView2 = new TextView(context);
//                        textView2.setText(document.getString("member2"));
//                        holder.gridLayout.addView(textView2);
//
//                        TextView textView3 = new TextView(context);
//                        textView3.setText(document.getString("member3"));
//                        holder.gridLayout.addView(textView1);
//
//                        TextView textView4 = new TextView(context);
//                        textView4.setText(document.getString("member4"));
//                        holder.gridLayout.addView(textView4);
//                    } else {
//                        Log.d("TAG", "No such document");
//                    }
//                } else {
//                    Log.d("TAG", "get failed with ", task.getException());
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {return partyArrayList.size();}

    public class PartyInformationViewHolder extends RecyclerView.ViewHolder {

        ImageView ott_icon;
        TextView subscription;
        Button id,password,price,accountNumber,accountHolderName,bankName;

        public PartyInformationViewHolder(@NonNull View itemView) {

            super(itemView);
            subscription = itemView.findViewById(R.id.subscription);

            bankName = itemView.findViewById(R.id.bankName);
            price = itemView.findViewById(R.id.price);
            id = itemView.findViewById(R.id.id);
            password = itemView.findViewById(R.id.password);
            accountHolderName = itemView.findViewById(R.id.accountHolderName);
            accountNumber = itemView.findViewById(R.id.accountNumber);

            ott_icon = itemView.findViewById(R.id.ott_icon);
        }
    }
}
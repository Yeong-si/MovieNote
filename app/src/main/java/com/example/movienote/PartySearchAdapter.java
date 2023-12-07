package com.example.movienote;

import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartySearchAdapter extends RecyclerView.Adapter<PartySearchAdapter.PartySearchViewHolder> {

    Context context;
    ArrayList<Party> partyArrayList;
    String user;

    public PartySearchAdapter(Context context,ArrayList<Party> partyArrayList) {
        this.context = context;
        this.partyArrayList = partyArrayList;
    }

    @NonNull
    @Override
    public PartySearchAdapter.PartySearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_party,parent,false);

        return new PartySearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartySearchAdapter.PartySearchViewHolder holder, int position) {

        Party party = partyArrayList.get(position);

        holder.member_size.setText(Integer.toString(party.getMember().size()));

//        FirebaseFirestore.getInstance().collection("Member").document(party.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        holder.member_size.setText(document.getString("member_size"));
//                    } else {
//                        Log.d("TAG", "No such document");
//                    }
//                } else {
//                    Log.d("TAG", "get failed with ", task.getException());
//                }
//            }
//        });

        holder.price.setText(party.getPrice());
        holder.subscription.setText(party.getSubscription());

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                party.getMember().add(user);
                Intent intent = new Intent(v.getContext(), PartyInformationActivity.class);
                v.getContext().startActivity(intent);
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
    }

    @Override
    public int getItemCount() {return partyArrayList.size();}

    public static class PartySearchViewHolder extends RecyclerView.ViewHolder {

        TextView price,member_size,subscription;
        Button button;
        ImageView ott_icon;
        public PartySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            member_size = itemView.findViewById(R.id.member_size);
            subscription = itemView.findViewById(R.id.subscription);

            ott_icon = itemView.findViewById(R.id.ott_icon);

            button = itemView.findViewById(R.id.button);
        }
    }
}

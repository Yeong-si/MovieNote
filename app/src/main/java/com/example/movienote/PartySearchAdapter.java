package com.example.movienote;

import android.content.Context;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PartySearchAdapter extends RecyclerView.Adapter<PartySearchAdapter.PartySearchViewHolder> {

    Context context;
    ArrayList<Party> partyArrayList;

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

        holder.member_size.setText(party.getMember().size());
        holder.price.setText(party.getPrice().toString());
        holder.subscription.setText(party.getSubscription());
    }

    @Override
    public int getItemCount() {return partyArrayList.size();}

    public static class PartySearchViewHolder extends RecyclerView.ViewHolder {

        TextView title,price,member_size,subscription;
        public PartySearchViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            member_size = itemView.findViewById(R.id.member_size);
            subscription = itemView.findViewById(R.id.subscription);
        }
    }
}

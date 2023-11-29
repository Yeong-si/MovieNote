package com.example.movienote;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PartyInformationAdapter extends RecyclerView.Adapter<PartyInformationAdapter.PartyInformationViewHolder> {

    Context context;
    ArrayList<Party> partyArrayList;

    public PartyInformationAdapter(Context context,ArrayList<Party> partyArrayList) {
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

        holder.member_size.setText(party.getMember().size());
        holder.accountHolderName.setText(party.getAccountHolderName());
        holder.price.setText(party.getPrice().toString());
        holder.subscription.setText(party.getSubscription());
        holder.id.setText(party.getId());
        holder.password.setText(party.getPassword());
        holder.accountNumber.setText(party.getAccountNumber().toString());
        holder.bankName.setText(party.getBankName());

        for (int i=0;i<party.getMember().size();i++){
            TextView textView = new TextView(context);
            textView.setText(party.getMember().get(i));
            holder.gridLayout.addView(textView);
        }
    }

    @Override
    public int getItemCount() {return partyArrayList.size();}

    public static class PartyInformationViewHolder extends RecyclerView.ViewHolder {

        TextView subscription,member_size,price,id,password,accountHolderName,accountNumber,bankName;
        ImageView ott_icon;
        GridLayout gridLayout;
        public PartyInformationViewHolder(@NonNull View itemView) {

            super(itemView);
            subscription = itemView.findViewById(R.id.subscription);
            member_size = itemView.findViewById(R.id.member_size);
            price = itemView.findViewById(R.id.price);
            id = itemView.findViewById(R.id.id);
            password = itemView.findViewById(R.id.password);
            accountHolderName = itemView.findViewById(R.id.accountHolderName);
            accountNumber = itemView.findViewById(R.id.accountNumber);

            ott_icon = itemView.findViewById(R.id.ott_icon);

            gridLayout = itemView.findViewById(R.id.gridLayout);
        }
    }
}

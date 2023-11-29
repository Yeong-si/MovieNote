package com.example.movienote;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PartyInformationAdapter.PartyInformationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PartyInformationViewHolder extends RecyclerView.ViewHolder {


        public PartyInformationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

package com.example.movienote;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
                }, 3000);  // 5000 밀리초(5초) 후에 실행
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
                }, 3000);  // 5000 밀리초(5초) 후에 실행
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
                }, 3000);  // 5000 밀리초(5초) 후에 실행
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
                }, 3000);  // 5000 밀리초(5초) 후에 실행
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
                }, 3000);  // 5000 밀리초(5초) 후에 실행
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
                }, 3000);  // 5000 밀리초(5초) 후에 실행
            }
        });

        for (int i=0;i<party.getMember().size();i++){
            TextView textView = new TextView(context);
            textView.setText(party.getMember().get(i));
            holder.gridLayout.addView(textView);
        }
    }

    @Override
    public int getItemCount() {return partyArrayList.size();}

    public class PartyInformationViewHolder extends RecyclerView.ViewHolder {

        ImageView ott_icon;
        GridLayout gridLayout;
        Button id,password,price,accountNumber,accountHolderName,subscription,bankName;

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

            gridLayout = itemView.findViewById(R.id.gridLayout);
        }
    }
}



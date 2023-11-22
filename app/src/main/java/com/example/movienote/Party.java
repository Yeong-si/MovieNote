package com.example.movienote;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;

public class Party {
    private String title;

    private String subscription;

    private List<String> member;

    private Long accountNumber;

    private String bankName;

    private String accountHolderName;

    private Long price;

    public Party() {}

    public Party(String title,String subscription,List<String> member,Long accountNumber,String bankName,String accountHolderName,Long price) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.member = member;
        this.price = price;
        this.subscription = subscription;
        this.title = title;
    }

    public String getAccountHolderName() {return accountHolderName;}

    public void setAccountHolerName(String accountHolderName) {this.accountHolderName = accountHolderName;}

    public Long getAccountNumber() {return accountNumber;}

    public void setAccountNumber(Long accountNumber) {this.accountNumber = accountNumber;}

    public String getBankName() {return bankName;}

    public void setBankName(String bankName) {this.bankName = bankName;}
}

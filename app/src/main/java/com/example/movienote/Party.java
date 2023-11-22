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
    private String id;
    private String password;

    public Party() {}
    public Party(String title,String subscription,List<String> member,Long accountNumber,String bankName,String accountHolderName,Long price,String id,String password) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.member = member;
        this.price = price;
        this.subscription = subscription;
        this.title = title;
        this.id = id;
        this.password = password;
    }

    public String getAccountHolderName() {return accountHolderName;}
    public void setAccountHolerName(String accountHolderName) {this.accountHolderName = accountHolderName;}
    public Long getAccountNumber() {return accountNumber;}
    public void setAccountNumber(Long accountNumber) {this.accountNumber = accountNumber;}
    public String getBankName() {return bankName;}
    public void setBankName(String bankName) {this.bankName = bankName;}
    public List<String> getMember() {return member;}
    public void setMember(List<String> member) {this.member = member;}
    public  Long getPrice() {return price;}
    public void setPrice(Long price) {this.price = price;}
    public String getSubscription() {return subscription;}
    public void setSubscription(String subscription) {this.subscription = subscription;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
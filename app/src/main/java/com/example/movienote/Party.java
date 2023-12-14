// firestore를 위한 Party 컬랙션을 클래스로 구현함

package com.example.movienote;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Party {
    private String subscription; // 구독할 요금제 종류
    private String accountNumber; // 계좌번호
    private String bankName; // 이체할 은행이름
    private String accountHolderName; // 수취인
    private String price; // 월 청구액
    private String id; // 공유하는 아이디
    private String password; // 공유하는 비밀번호
    private ArrayList<String> member;

    public Party() {}

    public Party(ArrayList<String> member,String subscription, String accountNumber, String bankName, String accountHolderName, String price, String id, String password) {
        this.subscription = subscription;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.accountHolderName = accountHolderName;
        this.price = price;
        this.id = id;
        this.password = password;
        this.member = member;
    }

    public ArrayList<String> getMember() {
        return member;
    }

    public void setMember(ArrayList<String> member) {
        this.member = member;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
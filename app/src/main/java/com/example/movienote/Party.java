// firestore를 위한 Party 컬랙션을 클래스로 구현함

package com.example.movienote;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;

public class Party {
    private String writer;
    private String title; // 파티원 구하는 글의 제목
    private String subscription; // 구독할 요금제 종류
    private List<String> member; // 현재 멤버
    private Long accountNumber; // 계좌번호
    private String bankName; // 이체할 은행이름
    private String accountHolderName; // 수취인
    private Long price; // 가격
    private String id; // 공유하는 아이디
    private String password; // 공유하는 비밀번호

    public Party() {}
    public Party(String writer,String title,String subscription,List<String> member,Long accountNumber,String bankName,String accountHolderName,Long price,String id,String password) {
        this.writer = writer;
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

    public String getWriter() {return writer;}
    public void setWriter(String writer) {this.writer = writer;}

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
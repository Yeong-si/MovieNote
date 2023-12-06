package com.example.movienote;

public class Member {
    private String member_size;
    private String member1;
    private String member2;
    private String member3;
    private String member4;

    public Member(){}

    public Member(String member_size, String member1, String member2, String member3, String member4) {
        this.member_size = member_size;
        this.member1 = member1;
        this.member2 = member2;
        this.member3 = member3;
        this.member4 = member4;
    }

    public String getMember_size() {
        return member_size;
    }

    public void setMember_size(String member_size) {
        this.member_size = member_size;
    }

    public String getMember1() {
        return member1;
    }

    public void setMember1(String member1) {
        this.member1 = member1;
    }

    public String getMember2() {
        return member2;
    }

    public void setMember2(String member2) {
        this.member2 = member2;
    }

    public String getMember3() {
        return member3;
    }

    public void setMember3(String member3) {
        this.member3 = member3;
    }

    public String getMember4() {
        return member4;
    }

    public void setMember4(String member4) {
        this.member4 = member4;
    }
}

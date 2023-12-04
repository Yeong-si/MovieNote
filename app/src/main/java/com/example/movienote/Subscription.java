package com.example.movienote;

public class Subscription {
    private String subscription;
    private String price;
    private String max;

    public Subscription(){}
    public Subscription(String subscription, String price, String max) {
        this.subscription = subscription;
        this.price = price;
        this.max = max;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}

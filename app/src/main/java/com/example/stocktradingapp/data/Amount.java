package com.example.stocktradingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Amount {
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency")
    @Expose
    private String currency;

    //CONSTRUCTOR
    public Amount(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    //GETTER
    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
    //SETTTER

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

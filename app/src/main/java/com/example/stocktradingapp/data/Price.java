package com.example.stocktradingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("amount")
    @Expose
    private Double amount;

    public Price(Double amount) {
        this.amount = amount;
    }

    //GETTER
    public String getCurrency() {
        return currency;
    }
    public Double getAmount() {
        return amount;
    }
    //SETTER
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

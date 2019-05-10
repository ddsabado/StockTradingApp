package com.example.stocktradingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InternalTransfer {

    @SerializedName("fromAccountId")
    @Expose
    private String fromAccountId;
    @SerializedName("toAccountId")
    @Expose
    private String toAccountId;
    @SerializedName("amount")
    @Expose
    private Amount amount;
    @SerializedName("narrative")
    @Expose
    private String narrative;

    public InternalTransfer(String fromAccountId, String toAccountId, Amount amount, String narrative) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.narrative = narrative;
    }

    //gettter and setter
    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }
}

package com.example.stocktradingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalTransfer {
    @SerializedName("fromAccountId")
    @Expose
    private String fromAccountId;
    @SerializedName("toAccountId")
    @Expose
    private String toAccountId;
    @SerializedName("payee")
    @Expose
    private String payee;
    @SerializedName("amount")
    @Expose
    private Amount amount;
    @SerializedName("narrative")
    @Expose
    private String narrative;
    //RESPONSE VARIABLES
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("transactionTime")
    @Expose
    private String transactionTime;

    //RESPONSE GETTER


    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    //CONSTRUCTOR
    public ExternalTransfer(String fromAccountId, String toAccountId, String payee, Amount amount, String narrative) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.payee = payee;
        this.amount = amount;
        this.narrative = narrative;
    }

    //GETTER
    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public String getPayee() {
        return payee;
    }

    public Amount getAmount() {
        return amount;
    }

    public String getNarrative() {
        return narrative;
    }
    //SETTER

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }
}

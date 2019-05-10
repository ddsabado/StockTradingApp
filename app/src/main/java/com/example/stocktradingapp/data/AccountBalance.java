package com.example.stocktradingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountBalance {
    @SerializedName("bookedBalance")
    @Expose
    private BookedBalance bookedBalance;
    @SerializedName("availableBalance")
    @Expose
    private AvailableBalance availableBalance;
    @SerializedName("overdraftLimit")

    // GETTER
    public BookedBalance getBookedBalance() {
        return bookedBalance;
    }

    public AvailableBalance getAvailableBalance() {
        return availableBalance;
    }


    // SETTER
    public void setBookedBalance(BookedBalance bookedBalance) {
        this.bookedBalance = bookedBalance;
    }

    public void setAvailableBalance(AvailableBalance availableBalance) {
        this.availableBalance = availableBalance;
    }

}

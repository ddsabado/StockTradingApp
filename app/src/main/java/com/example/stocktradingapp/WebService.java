package com.example.stocktradingapp;

import com.example.stocktradingapp.data.AccountBalance;
import com.example.stocktradingapp.data.ExternalTransfer;
import com.example.stocktradingapp.data.StockQuote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebService {

    @GET("stocks/{stockid}.json")
    Call<StockQuote> getStockQuote(@Path("stockid") String stockid);

    @GET("accounts/0001000100001/balances")
    Call<AccountBalance> getAccountBalance();

    @POST("external")
    Call<ExternalTransfer> getExternalTransfer(@Body ExternalTransfer externalTransfer);

}

package com.example.stocktradingapp;

import com.example.stocktradingapp.data.AccountBalance;
import com.example.stocktradingapp.data.ExternalTransfer;
import com.example.stocktradingapp.data.InternalTransfer;
import com.example.stocktradingapp.data.StockQuote;
import com.example.stocktradingapp.data.Transaction;

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

    @GET("accounts/0001000100001/transactions")
    Call<Transaction> getAccountTransactions();

    @POST("external")
    Call<ExternalTransfer> getExternalTransfer(@Body ExternalTransfer externalTransfer);

    @POST("internal")
    Call<InternalTransfer> getInternalTransfer(@Body InternalTransfer internalTransfer);

}

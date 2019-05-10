package com.example.stocktradingapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocktradingapp.R;
import com.example.stocktradingapp.Utils;
import com.example.stocktradingapp.WebService;
import com.example.stocktradingapp.data.Amount;
import com.example.stocktradingapp.data.ExternalTransfer;
import com.example.stocktradingapp.token.Storage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FundAccountTask extends AsyncTask<Void, Void, Void> {

    private EditText editTextAccFrom;
    private EditText editTextAccTo;
    private EditText editTextAmount;
    private EditText editTextCurrency;
    private EditText editTextNotes;
    private EditText editTextPayee;

    private String AccFrom;
    private String AccTo;
    private String Amount;
    private String Currency;
    private String Notes;
    private String Payee;

    private TextView tvTransactionId;
    private TextView tvTransactionDate;
    private TextView tvTransactionTime;
    private TextView tvTransactionDetails;

    private Context mContext;
    private OkHttpClient.Builder httpClient;

    public FundAccountTask(Context mContext, EditText editTextAccFrom, EditText editTextPayee, EditText editTextAccTo,
                           EditText editTextAmount, EditText editTextCurrency, EditText editTextNotes,
                           TextView tvTransactionId, TextView tvTransactionDate, TextView tvTransactionTime, TextView tvTransactionDetails) {
        this.mContext = mContext;
        this.editTextAccFrom = editTextAccFrom;
        this.editTextPayee = editTextPayee;
        this.editTextAccTo = editTextAccTo;
        this.editTextAmount = editTextAmount;
        this.editTextCurrency = editTextCurrency;
        this.editTextNotes = editTextNotes;
        this.tvTransactionId = tvTransactionId;
        this.tvTransactionDate = tvTransactionDate;
        this.tvTransactionTime = tvTransactionTime;
        this.tvTransactionDetails = tvTransactionDetails;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final String accessToken = new Storage(mContext).getAccessToken();

        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", accessToken).build();
                return chain.proceed(request);
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.baseTransferUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //CREATE POST BODY
        AccFrom = editTextAccFrom.getText().toString();
        AccTo = editTextAccTo.getText().toString();
        Amount = editTextAmount.getText().toString();
        Currency = editTextCurrency.getText().toString();
        Notes = editTextNotes.getText().toString();
        Payee = editTextPayee.getText().toString();

        Amount externalTransferAmount = new Amount(Amount, Currency);
        ExternalTransfer externalTransfer = new ExternalTransfer(AccFrom,AccTo,Payee,externalTransferAmount,Notes);

        WebService webService = retrofit.create(WebService.class);
        Call<ExternalTransfer> call = webService.getExternalTransfer(externalTransfer);
        call.enqueue(new Callback<ExternalTransfer>() {
            @Override
            public void onResponse(Call<ExternalTransfer> call, Response<ExternalTransfer> response) {
                Log.d("ExternalTrans Success", response.message() + response.body());
                Toast.makeText(mContext, response.code() + ":" + response.message(), Toast.LENGTH_LONG).show();
                if(response.body() != null) {
                    generateExternalTransferResponse(response.body());
                }
                else{
                    Toast.makeText(mContext, response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ExternalTransfer> call, Throwable t) {
                Log.d("ExternalTrans Fail", t.getMessage());
            }
        });
    }

    private void generateExternalTransferResponse(ExternalTransfer externalTransferResponse){
        tvTransactionDetails.setVisibility(View.VISIBLE);
        tvTransactionId.setText("Transaction ID: " + externalTransferResponse.getTransactionId());
        tvTransactionDate.setText("Transaction Date: " + externalTransferResponse.getTransactionDate());
        tvTransactionTime.setText("Transaction Time: " + externalTransferResponse.getTransactionTime());
    }
}

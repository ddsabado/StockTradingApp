package com.example.stocktradingapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stocktradingapp.Utils;
import com.example.stocktradingapp.WebService;
import com.example.stocktradingapp.data.Amount;
import com.example.stocktradingapp.data.ExternalTransfer;
import com.example.stocktradingapp.data.InternalTransfer;
import com.example.stocktradingapp.data.Stock;
import com.example.stocktradingapp.data.StockQuote;
import com.example.stocktradingapp.token.Storage;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuyTask extends AsyncTask<Void, Void, Void> {
    private OkHttpClient.Builder httpClient;

    private Context mContext;
    private EditText editText;
    //TODO: CONSTRUCTOR(Context, textview


    public BuyTask(Context mContext, EditText editText) {
        this.mContext = mContext;
        this.editText = editText;
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
        Retrofit retrofitquote = new Retrofit.Builder()
                .baseUrl(Utils.basePSEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService webService = retrofitquote.create(WebService.class);
        Call<StockQuote> call = webService.getStockQuote(editText.getText().toString());
        call.enqueue(new Callback<StockQuote>() {
            @Override
            public void onResponse(Call<StockQuote> call, Response<StockQuote> response) {
                Log.d("Stock Quote Success", response.message() + response.body());
                if(response.body() != null) {
                    Toast.makeText(mContext,response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    initbuy(response.body().getStock());
                }
                else{
                    Toast.makeText(mContext,response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<StockQuote> call, Throwable t) {
                Log.d("Stock Quote Failure", t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initbuy(List<Stock> stockResponse){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.baseTransferUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //edit post body
        Amount internalTransferAmount = new Amount(stockResponse.get(0).getPrice().getAmount().toString(), "USD");
        Log.d("PRICE",stockResponse.get(0).getPrice().getAmount().toString());
        InternalTransfer internalTransfer = new InternalTransfer("0001000100001","0001000100002",internalTransferAmount,"test");


        WebService webService = retrofit.create(WebService.class);
        Call<InternalTransfer> call = webService.getInternalTransfer(internalTransfer);
        call.enqueue(new Callback<InternalTransfer>() {
            @Override
            public void onResponse(Call<InternalTransfer> call, Response<InternalTransfer> response) {
                Log.d("InternalTrans Success", response.message() + response.body());
                Toast.makeText(mContext, "Account Balance Updated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<InternalTransfer> call, Throwable t) {
                Log.d("InternalTrans Fail", t.getMessage());
            }
        });
    }


}

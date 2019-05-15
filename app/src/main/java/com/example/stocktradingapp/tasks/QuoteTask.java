package com.example.stocktradingapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.stocktradingapp.R;
import com.example.stocktradingapp.Utils;
import com.example.stocktradingapp.WebService;
import com.example.stocktradingapp.adapter.QuoteAdapter;
import com.example.stocktradingapp.data.Stock;
import com.example.stocktradingapp.data.StockQuote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteTask extends AsyncTask<Void, Void, Void> {
    private static Context mContext;
    private RecyclerView rootView;
    private QuoteAdapter quoteAdapter;
    private EditText editText;
    private ImageView imgchart;
    private String stockCode;

    public QuoteTask(Context mContext, RecyclerView rootView,String stockCode, QuoteAdapter quoteAdapter, ImageView imgchart) {
        this.mContext = mContext;
        this.rootView = rootView;
        this.stockCode = stockCode;
        this.quoteAdapter = quoteAdapter;
        this.imgchart = imgchart;
    }


    public QuoteTask(Context mContext, RecyclerView rootView,EditText editText, QuoteAdapter quoteAdapter, ImageView imgchart) {
        this.mContext = mContext;
        this.rootView = rootView;
        this.editText = editText;
        this.quoteAdapter = quoteAdapter;
        this.imgchart = imgchart;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Create Webservice
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.basePSEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService webService = retrofit.create(WebService.class);
        Call<StockQuote> call = webService.getStockQuote(editText.getText().toString());
        call.enqueue(new Callback<StockQuote>() {
            @Override
            public void onResponse(Call<StockQuote> call, Response<StockQuote> response) {
                Log.d("Stock Quote Success", response.message() + response.body());
                if(response.body() != null) {
                    imgchart.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext,response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    generateStockQuoteData(response.body(), response.body().getStock());
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

    private void generateStockQuoteData(StockQuote stockQuoteResponse, List<Stock> stockListResponse){
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_quote);

        quoteAdapter = new QuoteAdapter(stockQuoteResponse, stockListResponse);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(quoteAdapter);
        quoteAdapter.notifyDataSetChanged();
    }
}

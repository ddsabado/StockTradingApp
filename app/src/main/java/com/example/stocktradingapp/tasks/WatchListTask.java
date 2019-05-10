package com.example.stocktradingapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stocktradingapp.R;
import com.example.stocktradingapp.Utils;
import com.example.stocktradingapp.WebService;
import com.example.stocktradingapp.adapter.WatchListAdapter;
import com.example.stocktradingapp.data.Price;
import com.example.stocktradingapp.data.Stock;
import com.example.stocktradingapp.data.StockQuote;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatchListTask extends AsyncTask<Void, Void, Void> {
    private static Context mContext;
    private RecyclerView rootView;
    private WatchListAdapter watchListAdapter;
    private EditText editText;
    private ArrayList<Stock> watchList;
    private Stock watchListStock;
    private Price watchListPrice;

    private Double amount;
    private String symbol;
    private Double percentChange;
    private Integer volume;



    public WatchListTask(Context mContext, RecyclerView rootView, EditText editText, WatchListAdapter watchListAdapter, ArrayList<Stock> watchList) {
        this.mContext = mContext;
        this.rootView = rootView;
        this.editText = editText;
        this.watchListAdapter = watchListAdapter;
        this.watchList = watchList;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.basePSEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService webService = retrofit.create(WebService.class);

        Call<StockQuote> call = webService.getStockQuote(editText.getText().toString());
        call.enqueue(new Callback<StockQuote>() {
            @Override
            public void onResponse(Call<StockQuote> call, Response<StockQuote> response) {
                Log.d("Watch List Success", response.message() + response.body());
                if(response.body() != null) {
                    Toast.makeText(mContext, response.code() + ":" + response.message(), Toast.LENGTH_LONG).show();
                    //insertToList(response.body().getStock());
                    addToWatchList(response.body().getStock());
                }
                else{
                    Toast.makeText(mContext,response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StockQuote> call, Throwable t) {
                Log.d("Watch List Failure", t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void addToWatchList(List<Stock> watchListResponse){
        int counter = 0;
        //set variables
        amount = watchListResponse.get(0).getPrice().getAmount();
        symbol = watchListResponse.get(0).getSymbol();
        percentChange = watchListResponse.get(0).getPercentChange();
        volume = watchListResponse.get(0).getVolume();

        Log.d("Array Size1", watchList.size() + "");
        //construct item to be added to watchlist
        watchListPrice = new Price(amount);
        watchListStock = new Stock(symbol,watchListPrice,percentChange,volume);
        //add to watchlist
        watchList.add(watchListStock);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_watchlist);
        Log.d("Array Size2", watchList.size() + "");
        watchListAdapter = new WatchListAdapter(watchList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(watchListAdapter);
        watchListAdapter.notifyDataSetChanged();
        //watchListAdapter.notifyItemInserted(watchList.size());
    }
/*
    private List<Stock> insertToList(List<Stock> watchListResponse){
        amount = watchListResponse.get(0).getPrice().getAmount();
        symbol = watchListResponse.get(0).getSymbol();
        percentChange = watchListResponse.get(0).getPercentChange();
        volume = watchListResponse.get(0).getVolume();


        Log.d("Price", amount.toString());
        Log.d("Array Size", watchListResponse.size() + "");
        watchListPrice = new Price(amount);
        watchListStock = new Stock(symbol,watchListPrice,percentChange,volume);
        watchList.add(watchListStock);
        Log.d("Array Size", watchListResponse.size() + "");

        return watchList;
    }
    */
}

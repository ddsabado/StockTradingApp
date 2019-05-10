package com.example.stocktradingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stocktradingapp.R;
import com.example.stocktradingapp.data.Stock;

import java.util.ArrayList;
import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder> {
    private ArrayList<Stock> stockListResponse;

    public  WatchListAdapter(){

    }

    public WatchListAdapter(ArrayList<Stock> stockListResponse) {
        this.stockListResponse = stockListResponse;
    }

    @Override
    public WatchListViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watchlist_row, parent, false);
        return new WatchListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WatchListViewHolder holder, int position) {
        Log.d("VH position", position + "");
        Stock stock =  stockListResponse.get(position);
        holder.tvSymbol.setText(stock.getSymbol());
        holder.tvPrice.setText(stock.getPrice().getAmount().toString() + " " + stock.getPrice().getCurrency());
        holder.tvPercentChange.setText(stock.getPercentChange().toString() + "%");
        holder.tvVolume.setText(stock.getVolume().toString());
    }

    @Override
    public int getItemCount() {
        try {
            Log.d("Adapter list size", stockListResponse.size() + "");
            return stockListResponse.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public class WatchListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSymbol;
        public TextView tvPrice;
        public TextView tvPercentChange;
        public TextView tvVolume;

        public WatchListViewHolder(View view) {
            super(view);
            tvSymbol = (TextView) view.findViewById(R.id.tvStockSymbol);
            tvPrice = (TextView) view.findViewById(R.id.tvStockPrice);
            tvPercentChange = (TextView) view.findViewById(R.id.tvPercentChangeWL);
            tvVolume = (TextView) view.findViewById(R.id.tvVolume);
        }
    }
}

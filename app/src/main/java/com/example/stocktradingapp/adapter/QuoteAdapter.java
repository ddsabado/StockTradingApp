package com.example.stocktradingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stocktradingapp.R;
import com.example.stocktradingapp.data.Stock;
import com.example.stocktradingapp.data.StockQuote;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteAdapterViewHolder> {
    private StockQuote stockQuoteResponse;
    private List<Stock> stockListResponse;

    public QuoteAdapter(StockQuote stockQuoteResponse, List<Stock> stockListResponse) {
        this.stockQuoteResponse = stockQuoteResponse;
        this.stockListResponse = stockListResponse;
    }

    @Override
    public QuoteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_quote_row, parent, false);
        return new QuoteAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuoteAdapterViewHolder holder, int position) {
        Log.d("AS OF", stockQuoteResponse.getAsOf());
        holder.tvSymbol.setText(stockListResponse.get(position).getSymbol());
        holder.tvName.setText(stockListResponse.get(position).getName());
        //holder.tvCurrency.setText(stockListResponse.get(position).getPrice().getCurrency());
        holder.tvAmount.setText(stockListResponse.get(position).getPrice().getAmount().toString() + " " + stockListResponse.get(position).getPrice().getCurrency());
        holder.tvPercentChange.setText(stockListResponse.get(position).getPercentChange().toString());
        holder.tvAsOf.setText("As of: " + stockQuoteResponse.getAsOf());
    }

    @Override
    public int getItemCount() {
        try {
            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public class QuoteAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSymbol;
        public TextView tvName;
        public TextView tvCurrency;
        public TextView tvAmount;
        public TextView tvPercentChange;
        public TextView tvAsOf;

        public QuoteAdapterViewHolder(View view) {
            super(view);
            tvSymbol = (TextView) view.findViewById(R.id.tvSymbol);
            tvName = (TextView) view.findViewById(R.id.tvStockName);
            tvCurrency = (TextView) view.findViewById(R.id.tvCurrency);
            tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            tvPercentChange = (TextView) view.findViewById(R.id.tvPercentChangeWL);
            tvAsOf = (TextView) view.findViewById(R.id.tvAsOf);
        }
    }
}

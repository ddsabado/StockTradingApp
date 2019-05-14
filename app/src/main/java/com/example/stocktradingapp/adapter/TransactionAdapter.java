package com.example.stocktradingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stocktradingapp.R;
import com.example.stocktradingapp.data.Item;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{
    private List<Item> itemsList;

    public TransactionAdapter(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transactions_row, parent, false);
        return new TransactionViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        holder.tvDate.setText(itemsList.get(position).getValueDate());
        holder.tvDescription.setText(itemsList.get(position).getDescription());
        holder.tvAmount.setText(itemsList.get(position).getAmount().getAmount());
        holder.tvCurrency.setText(itemsList.get(position).getAmount().getCurrency());
        holder.tvIndicator.setText(itemsList.get(position).getCreditDebitIndicator());
    }

    @Override
    public int getItemCount() {
        try {
            return itemsList.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate;
        public TextView tvDescription;
        public TextView tvCurrency;
        public TextView tvAmount;
        public TextView tvIndicator;


        public TransactionViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            tvCurrency = (TextView) view.findViewById(R.id.tvCurrency);
            tvAmount = (TextView) view.findViewById(R.id.tvAmount);
            tvIndicator = (TextView) view.findViewById(R.id.tvIndicator);

        }
    }
}

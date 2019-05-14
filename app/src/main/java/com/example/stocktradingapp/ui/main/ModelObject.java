package com.example.stocktradingapp.ui.main;

import com.example.stocktradingapp.R;

public enum ModelObject {
    ACCOUNTS(R.string.Accounts ,R.layout.view_account),
    TRANSACTIONS(R.string.Transactions, R.layout.view_transactions),
    QUOTE(R.string.Quote, R.layout.view_quote),
    WATCHLIST(R.string.WatchList, R.layout.view_watchlist),
    TRADE(R.string.Trade, R.layout.view_trade),
    STOCKMARKET(R.string.StockMarket, R.layout.view_stockmarket);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }
}

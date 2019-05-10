package com.example.stocktradingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * {
 *     "stock": [
 *         {
 *             "name": "Banco de Oro",
 *             "price": {
 *                 "currency": "PHP",
 *                 "amount": 135.1
 *             },
 *             "percent_change": 0.07,
 *             "volume": 208340,
 *             "symbol": "BDO"
 *         }
 *     ],
 *     "as_of": "2019-05-07T10:34:00+08:00"
 * }
 */
public class StockQuote {
    @SerializedName("stock")
    @Expose
    private List<Stock> stock = null;
    @SerializedName("as_of")
    @Expose
    private String asOf;

    //GETTER
    public List<Stock> getStock() {
        return stock;
    }
    public String getAsOf() {
        return asOf;
    }

    //SETTER

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }
    public void setAsOf(String asOf) {
        this.asOf = asOf;
    }
}

package com.example.stocktradingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("percent_change")
    @Expose
    private Double percentChange;
    @SerializedName("volume")
    @Expose
    private Integer volume;
    @SerializedName("symbol")
    @Expose
    private String symbol;

    public Stock(String symbol, Price price, Double percentChange, Integer volume) {
        this.price = price;
        this.percentChange = percentChange;
        this.volume = volume;
        this.symbol = symbol;
    }

    //GETTER

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public Integer getVolume() {
        return volume;
    }

    public String getSymbol() {
        return symbol;
    }

    //SETTER

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

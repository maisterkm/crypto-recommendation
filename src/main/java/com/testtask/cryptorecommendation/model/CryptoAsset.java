package com.testtask.cryptorecommendation.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CryptoAsset {
    private LocalDate date;
    private String symbol;
    private BigDecimal price;
    public BigDecimal normalizedRange;

    public CryptoAsset() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNormalizedRange() {
        return normalizedRange;
    }

    public void setNormalizedRange(BigDecimal normalizedRange) {
        this.normalizedRange = normalizedRange;
    }

    @Override
    public String toString() {
        return "CryptoAsset{" +
                "date=" + date +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                ", normalizedRange=" + normalizedRange +
                '}';
    }
}

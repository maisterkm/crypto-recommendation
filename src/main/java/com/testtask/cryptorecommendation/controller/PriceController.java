package com.testtask.cryptorecommendation.controller;

import com.testtask.cryptorecommendation.model.CryptoAsset;
import com.testtask.cryptorecommendation.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/{symbol}/oldest")
    public CryptoAsset getOldestPrice(@PathVariable String symbol) {
        return priceService.getOldestPrice(symbol);
    }

    @GetMapping("/{symbol}/newest")
    public CryptoAsset getNewestPrice(@PathVariable String symbol) {
        return priceService.getNewestPrice(symbol);
    }

    @GetMapping("/{symbol}/min")
    public CryptoAsset getMinPrice(@PathVariable String symbol) {
        return priceService.getMinPrice(symbol);
    }

    @GetMapping("/{symbol}/max")
    public CryptoAsset getMaxPrice(@PathVariable String symbol) {
        return priceService.getMaxPrice(symbol);
    }


    @GetMapping("/range")
    public List<CryptoAsset> getSortedCryptosByNormalizedRange() {
        return priceService.getSortedCryptosByNormalizedRange();
    }

    @GetMapping("/{date}/highest-range")
    public CryptoAsset getCryptoWithHighestRangeByDay(@PathVariable String date) {
        return priceService.getCryptoWithHighestRangeByDay(date);
    }
}

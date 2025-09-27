package com.example.crypto_trading.controller;

import com.example.crypto_trading.model.AggregatedPrice;
import com.example.crypto_trading.repository.AggregatedPriceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final AggregatedPriceRepository aggregatedPriceRepository;

    public ApiController(AggregatedPriceRepository aggregatedPriceRepository) {
        this.aggregatedPriceRepository = aggregatedPriceRepository;
    }

    @GetMapping("/prices")
    public List<AggregatedPrice> getAggregatedPriceList() {
        return aggregatedPriceRepository.findAll();
    }

    @GetMapping("/prices/{pair}")
    public AggregatedPrice getAggregatedPriceByPair(@PathVariable String pair) {
        return aggregatedPriceRepository.findByPair(pair.toUpperCase())
                .orElseThrow(() -> new RuntimeException("No price for " + pair));
    }
}

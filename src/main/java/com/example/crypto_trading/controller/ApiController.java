package com.example.crypto_trading.controller;

import com.example.crypto_trading.model.AggregatedPrice;
import com.example.crypto_trading.model.Wallet;
import com.example.crypto_trading.repository.AggregatedPriceRepository;
import com.example.crypto_trading.repository.WalletRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final WalletRepository walletRepository;

    public ApiController(AggregatedPriceRepository aggregatedPriceRepository, WalletRepository walletRepository) {
        this.aggregatedPriceRepository = aggregatedPriceRepository;
        this.walletRepository = walletRepository;
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

    @GetMapping("/wallet/{userId}")
    public List<Wallet> getUsersWalletById(@PathVariable Long userId) {
        return walletRepository.findByUsersId(userId);
    }

    @GetMapping("/wallet")
    public List<Wallet> getUsersWalletList() {
        return walletRepository.findAll();
    }
}

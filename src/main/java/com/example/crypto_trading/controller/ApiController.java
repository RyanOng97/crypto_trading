package com.example.crypto_trading.controller;

import com.example.crypto_trading.dto.TradeRequestDTO;
import com.example.crypto_trading.model.AggregatedPrice;
import com.example.crypto_trading.model.TradeTransaction;
import com.example.crypto_trading.model.Wallet;
import com.example.crypto_trading.repository.AggregatedPriceRepository;
import com.example.crypto_trading.repository.TradeTransactionRepository;
import com.example.crypto_trading.repository.WalletRepository;
import com.example.crypto_trading.service.TradeServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final WalletRepository walletRepository;
    private final TradeServices tradeServices;
    private final TradeTransactionRepository tradeTransactionRepository;

    public ApiController(AggregatedPriceRepository aggregatedPriceRepository, WalletRepository walletRepository, TradeServices tradeServices, TradeTransactionRepository tradeTransactionRepository) {
        this.aggregatedPriceRepository = aggregatedPriceRepository;
        this.walletRepository = walletRepository;
        this.tradeServices = tradeServices;
        this.tradeTransactionRepository = tradeTransactionRepository;
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

    @PostMapping("/trade")
    public TradeTransaction trade(@RequestBody TradeRequestDTO tradeRequestDTO) {
        return tradeServices.trade(tradeRequestDTO);
    }

    @GetMapping("/tradeHistory/{userId}")
    public List<TradeTransaction> getUsersTradeTransactionById(@PathVariable Long userId) {
        return tradeTransactionRepository.findByUserId(userId);
    }

    @GetMapping("/tradeHistory")
    public List<TradeTransaction> getUsersTradeTransactionList() {
        return tradeTransactionRepository.findAll();
    }
}

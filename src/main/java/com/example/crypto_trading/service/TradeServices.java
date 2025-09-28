package com.example.crypto_trading.service;

import com.example.crypto_trading.dto.TradeRequestDTO;
import com.example.crypto_trading.model.AggregatedPrice;
import com.example.crypto_trading.model.TradeTransaction;
import com.example.crypto_trading.model.Users;
import com.example.crypto_trading.model.Wallet;
import com.example.crypto_trading.repository.AggregatedPriceRepository;
import com.example.crypto_trading.repository.TradeTransactionRepository;
import com.example.crypto_trading.repository.UsersRepository;
import com.example.crypto_trading.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Service
public class TradeServices {
    private final WalletRepository walletRepo;
    private final AggregatedPriceRepository priceRepo;
    private final TradeTransactionRepository txnRepo;
    private final UsersRepository usersRepository;

    public TradeServices(WalletRepository walletRepo, AggregatedPriceRepository priceRepo, TradeTransactionRepository txnRepo, UsersRepository usersRepository) {
        this.walletRepo = walletRepo;
        this.priceRepo = priceRepo;
        this.txnRepo = txnRepo;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public TradeTransaction trade(TradeRequestDTO tradeRequest) {
        Users user = usersRepository.findById(tradeRequest.getUserId())
                .orElseThrow(() -> new RuntimeException(("Did not have this user")));
        AggregatedPrice price = priceRepo.findByPair(tradeRequest.getPair())
                .orElseThrow(() -> new RuntimeException("No price"));

        BigDecimal tradePrice, finalPrice, prevBalance, updatedBalance, cryptoPrevBalance, cryptoUpdatedBalance;

        Wallet usdt = walletRepo.findByUsersIdAndCurrency(tradeRequest.getUserId(), "USDT");
        Wallet crypto = walletRepo.findByUsersIdAndCurrency(tradeRequest.getUserId(), tradeRequest.getPair().startsWith("BTC") ? "BTC" : "ETH");

        prevBalance = usdt.getBalance();
        cryptoPrevBalance = crypto.getBalance();

        if (tradeRequest.getDirection().equalsIgnoreCase("BUY")) {
            if (usdt.getBalance().compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("Insufficient USDT");
            if (tradeRequest.getOrderPrice().compareTo(usdt.getBalance()) > 0) throw new RuntimeException("Order price exceeds available USDT balance");
            tradePrice = price.getBestAsk();
            finalPrice = tradeRequest.getOrderPrice().divide(tradePrice, 8, RoundingMode.HALF_UP);
            updatedBalance = usdt.getBalance().subtract(tradeRequest.getOrderPrice());
            cryptoUpdatedBalance = crypto.getBalance().add(finalPrice);
            usdt.setBalance(updatedBalance);
            crypto.setBalance(cryptoUpdatedBalance);
        } else if (tradeRequest.getDirection().equalsIgnoreCase("SELL")) {
            if (crypto.getBalance().compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("Insufficient crypto");
            if (tradeRequest.getOrderPrice().compareTo(crypto.getBalance()) > 0) throw new RuntimeException("Order price exceeds available crypto balance");
            tradePrice = price.getBestBid();
            finalPrice = tradeRequest.getOrderPrice().multiply(tradePrice);
            cryptoUpdatedBalance = crypto.getBalance().subtract(tradeRequest.getOrderPrice());
            updatedBalance = usdt.getBalance().add(finalPrice);
            crypto.setBalance(cryptoUpdatedBalance);
            usdt.setBalance(updatedBalance);
        } else {
            throw new RuntimeException("Do not have such trade direction " + tradeRequest.getDirection());
        }

        walletRepo.save(usdt);
        walletRepo.save(crypto);

        TradeTransaction tx = new TradeTransaction();
        tx.setUserId(tradeRequest.getUserId());
        tx.setUserName(user.getUserName());
        tx.setPair(tradeRequest.getPair());
        tx.setDirection(tradeRequest.getDirection().toUpperCase());
        tx.setOrderPrice(tradeRequest.getOrderPrice());
        tx.setTradePrice(tradePrice);
        tx.setFinalPrice(finalPrice);
        tx.setCurrency("USDT");
        tx.setPrevBalance(prevBalance);
        tx.setUpdatedBalance(updatedBalance);
        tx.setCryptoCurrency(tradeRequest.getPair().startsWith("BTC") ? "BTC" : "ETH");
        tx.setCryptoPrevBalance(cryptoPrevBalance);
        tx.setCryptoUpdatedBalance(cryptoUpdatedBalance);
        tx.setCreatedAt(Instant.now());
        return txnRepo.save(tx);
    }
}

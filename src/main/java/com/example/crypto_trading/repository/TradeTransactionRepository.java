package com.example.crypto_trading.repository;

import com.example.crypto_trading.model.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
}

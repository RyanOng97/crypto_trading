package com.example.crypto_trading.repository;

import com.example.crypto_trading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findByUsersId(Long userId);
    Wallet findByUsersIdAndCurrency(Long userId, String currency);
}
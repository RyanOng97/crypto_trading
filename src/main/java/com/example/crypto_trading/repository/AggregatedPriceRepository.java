package com.example.crypto_trading.repository;

import com.example.crypto_trading.model.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AggregatedPriceRepository extends JpaRepository<AggregatedPrice, Long> {
    Optional<AggregatedPrice> findByPair(String pair);
}

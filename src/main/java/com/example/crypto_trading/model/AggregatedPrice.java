package com.example.crypto_trading.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "aggregated_price")
public class AggregatedPrice {

    @Id
    @GeneratedValue
    private long id;
    private String pair;
    private BigDecimal bestBid;
    private BigDecimal bestAsk;
    private Instant fetchedAt;
    private Instant createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public BigDecimal getBestBid() {
        return bestBid;
    }

    public void setBestBid(BigDecimal bestBid) {
        this.bestBid = bestBid;
    }

    public BigDecimal getBestAsk() {
        return bestAsk;
    }

    public void setBestAsk(BigDecimal bestAsk) {
        this.bestAsk = bestAsk;
    }

    public Instant getFetchedAt() {
        return fetchedAt;
    }

    public void setFetchedAt(Instant fetchedAt) {
        this.fetchedAt = fetchedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

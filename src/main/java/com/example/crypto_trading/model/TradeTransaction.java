package com.example.crypto_trading.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "trade_transaction")
public class TradeTransaction {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String userName;
    private String pair;
    private String direction;
    private BigDecimal orderPrice;
    private BigDecimal tradePrice;
    private BigDecimal finalPrice;
    private String currency;
    private BigDecimal prevBalance;
    private BigDecimal updatedBalance;
    private String cryptoCurrency;
    private BigDecimal cryptoPrevBalance;
    private BigDecimal cryptoUpdatedBalance;
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrevBalance() {
        return prevBalance;
    }

    public void setPrevBalance(BigDecimal prevBalance) {
        this.prevBalance = prevBalance;
    }

    public BigDecimal getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(BigDecimal updatedBalance) {
        this.updatedBalance = updatedBalance;
    }

    public String getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(String cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public BigDecimal getCryptoPrevBalance() {
        return cryptoPrevBalance;
    }

    public void setCryptoPrevBalance(BigDecimal cryptoPrevBalance) {
        this.cryptoPrevBalance = cryptoPrevBalance;
    }

    public BigDecimal getCryptoUpdatedBalance() {
        return cryptoUpdatedBalance;
    }

    public void setCryptoUpdatedBalance(BigDecimal cryptoUpdatedBalance) {
        this.cryptoUpdatedBalance = cryptoUpdatedBalance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

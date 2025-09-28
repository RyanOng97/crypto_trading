# Crypto Trading System

A simple crypto trading system built with **Spring Boot**, using **H2 in-memory database**.  
Supports trading of **BTCUSDT** and **ETHUSDT**, with automatic price aggregation from Binance and Huobi.

---

## Features

- Users can:
    - Buy/sell supported crypto trading pairs
    - View their wallet balances
    - Retrieve their trading history
- Automatic price aggregation every 10 seconds from:
    - Binance: `https://api.binance.com/api/v3/ticker/bookTicker`
    - Huobi: `https://api.huobi.pro/market/tickers`
- Best bid/ask prices stored in database
- User wallet initialized automatically on registration
- API-based interaction (REST)

---

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database
- REST API (Spring Web)
- Maven for build

---

package com.example.crypto_trading.scheduler;

import com.example.crypto_trading.dto.BinanceTickerDTO;

import com.example.crypto_trading.dto.HuobiResponseDTO;
import com.example.crypto_trading.dto.HuobiTickerDTO;
import com.example.crypto_trading.model.AggregatedPrice;
import com.example.crypto_trading.repository.AggregatedPriceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Component
public class PriceAggregatorScheduler {

    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public PriceAggregatorScheduler(AggregatedPriceRepository aggregatedPriceRepository) {
        this.aggregatedPriceRepository = aggregatedPriceRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void fetchPrices() {
        try {
            // Binance
            String binanceUrl = "https://api.binance.com/api/v3/ticker/bookTicker";
            List<BinanceTickerDTO> binanceList = Optional.ofNullable(
                    restTemplate.getForObject(binanceUrl, BinanceTickerDTO[].class)
            ).map(Arrays::asList).orElse(Collections.emptyList());

            Map<String, Map<String, BigDecimal>> aggregatedPrices = new HashMap<>();

            for (BinanceTickerDTO binance : binanceList) {
                String symbol = binance.getSymbol();
                if ("BTCUSDT".equals(symbol) || "ETHUSDT".equals(symbol)) {
                    aggregatedPrices.put(symbol, Map.of("bid", binance.getBidPrice(), "ask", binance.getAskPrice()));
                }
            }

            // Huobi
            String huobiUrl = "https://api.huobi.pro/market/tickers";
            HuobiResponseDTO huobiResponse = restTemplate.getForObject(huobiUrl, HuobiResponseDTO.class);
            List<HuobiTickerDTO> huobiList = Optional.ofNullable(huobiResponse)
                    .map(HuobiResponseDTO::getData)
                    .orElse(Collections.emptyList());

            for (HuobiTickerDTO huobi : huobiList) {
                String symbol = huobi.getSymbol().toUpperCase();
                if ("BTCUSDT".equals(symbol) || "ETHUSDT".equals(symbol)) {
                    BigDecimal bid = huobi.getBid();
                    BigDecimal ask = huobi.getAsk();

                    if (aggregatedPrices.containsKey(symbol)) {
                        // if binance got return data, then will compare the best prices
                        BigDecimal bestBid = aggregatedPrices.get(symbol).get("bid").max(bid);
                        BigDecimal bestAsk = aggregatedPrices.get(symbol).get("ask").min(ask);
                        aggregatedPrices.put(symbol, Map.of("bid", bestBid, "ask", bestAsk));
                    } else {
                        // if binance return empty list, and huobi got data, it will just insert huobi price
                        aggregatedPrices.put(symbol, Map.of("bid", bid, "ask", ask));
                    }
                }
            }

            // Save aggregated
            for (String pair : aggregatedPrices.keySet()) {
                // Find existing price or create a new one, setting createdAt only for new ones
                AggregatedPrice ap = aggregatedPriceRepository.findByPair(pair)
                        .orElseGet(() -> {
                            AggregatedPrice newPrice = new AggregatedPrice();
                            newPrice.setPair(pair);
                            newPrice.setCreatedAt(Instant.now());
                            return newPrice;
                        });

                // Update fields that change on every fetch
                ap.setBestBid(aggregatedPrices.get(pair).get("bid"));
                ap.setBestAsk(aggregatedPrices.get(pair).get("ask"));
                ap.setFetchedAt(Instant.now());

                aggregatedPriceRepository.save(ap);
            }
        } catch (Exception e) {
            System.err.println("Price fetch failed: " + e.getMessage());
        }
    }
}

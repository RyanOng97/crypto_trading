package com.example.crypto_trading.dto;

import java.util.List;

public class HuobiResponseDTO {
    private String status;
    private List<HuobiTickerDTO> data;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HuobiTickerDTO> getData() {
        return data;
    }

    public void setData(List<HuobiTickerDTO> data) {
        this.data = data;
    }
}
package com.example.crypto_trading.config;

import com.example.crypto_trading.model.Users;
import com.example.crypto_trading.model.Wallet;
import com.example.crypto_trading.repository.UsersRepository;
import com.example.crypto_trading.repository.WalletRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer {
    private final UsersRepository usersRepository;
    private final WalletRepository walletRepository;

    public DataInitializer(UsersRepository usersRepository, WalletRepository walletRepository) {
        this.usersRepository = usersRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initDbData() {
        if (usersRepository.count() == 0) {
            Users user = new Users();
            user.setUserName("chunwei");
            usersRepository.save(user);

            walletRepository.saveAll(Arrays.asList(
                    new Wallet(user, "USDT", new BigDecimal("50000.00")),
                    new Wallet(user, "BTC", BigDecimal.ZERO),
                    new Wallet(user, "ETH", BigDecimal.ZERO)
            ));
        }
    }
}

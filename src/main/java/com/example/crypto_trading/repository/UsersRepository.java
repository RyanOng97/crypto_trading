package com.example.crypto_trading.repository;

import com.example.crypto_trading.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long userId);
    Optional<Users> findByUserName(String userName);
}

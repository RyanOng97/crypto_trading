package com.example.crypto_trading.repository;

import com.example.crypto_trading.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

}

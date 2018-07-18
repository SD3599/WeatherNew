package com.WeatherApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WeatherApp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
}

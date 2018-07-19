package com.WeatherApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.WeatherApp.model.User;
/**
 * This repository extends JpaRepository for required methods
 * @author Swapnika
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
}

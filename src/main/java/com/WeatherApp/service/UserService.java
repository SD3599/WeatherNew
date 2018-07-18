package com.WeatherApp.service;

import com.WeatherApp.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}

package com.WeatherApp.service;

import com.WeatherApp.model.User;
/**
 * This interface provides custom methods for save and find user by username.
 * @author Swapnika
 *
 */

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}

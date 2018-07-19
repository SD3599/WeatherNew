package com.WeatherApp.service;
/**
 * This interface provides custom methods to find in logged in username at any point and 
 * to enable autologin once registration is done.
 * @author Swapnika
 *
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}

package com.WeatherApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.WeatherApp.model.User;
import com.WeatherApp.repository.UserRepository;

import java.util.HashSet;
/**
 * Implements user Service interface
 * @author Swapnika
 *
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
/**
 * used to save a user entity to the user table in the database
 * @param user entity to be saved
 */
    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
  //      user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }
/**
 * used to find a user entity by username provided 
 * @param string username to find the user entity by the username
 */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

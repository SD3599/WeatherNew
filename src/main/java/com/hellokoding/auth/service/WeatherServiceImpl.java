package com.hellokoding.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellokoding.auth.model.Weather;
import com.hellokoding.auth.repository.WeatherRepository;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;
  

 

	@Override
	public void save(Weather weather) {
		weatherRepository.save(weather);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Weather findByPlace(String place) {
		// TODO Auto-generated method stub
		 return weatherRepository.findByPlace(place);
		
	}
}


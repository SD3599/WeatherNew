package com.hellokoding.auth.service;


import com.hellokoding.auth.model.Weather;

public interface WeatherService {
	 void save(Weather weather);

	    Weather findByPlace(String place);
}

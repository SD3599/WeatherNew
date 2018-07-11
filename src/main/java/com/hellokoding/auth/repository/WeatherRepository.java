package com.hellokoding.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellokoding.auth.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
	Weather findByPlace(String place);
}

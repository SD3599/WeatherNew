package com.WeatherApp.service;

/**
 * This interface is to provide template for getting the forecast 
 * 
 * @author Swapnika
 *
 * @param <T> 
 */
public interface IWeatherService<T>{

    public T getForecast(String city,int Degree) throws Exception;

}

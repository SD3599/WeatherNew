package com.WeatherApp.service;


public interface IWeatherService<T>{

    public T getForecast(String city,int Degree) throws Exception;

}

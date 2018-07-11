package com.hellokoding.auth.service;


public interface IWeatherService<T>{

    public T getForecast(String city,int Degree) throws Exception;

}

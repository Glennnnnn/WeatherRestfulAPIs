package com.glenn.weatherRestfulAPIs.service;

/**
 * @Author Liu Jialin
 * @Date 2025/3/27 15:49
 * @PackageName com.glenn.weatherRestfulAPIs.service
 * @ClassName WeatherService
 * @Description TODO
 * @Version 1.0.0
 */
public interface WeatherService {
    String requestForWeatherData(String city, String country, String apiKey);
}

package com.glenn.weatherRestfulAPIs.service.Impl;

import com.alibaba.fastjson2.JSONObject;
import com.glenn.weatherRestfulAPIs.dao.ApiKeyRepository;
import com.glenn.weatherRestfulAPIs.dao.WeatherDataRepository;
import com.glenn.weatherRestfulAPIs.entity.dbOrm.ApiKey;
import com.glenn.weatherRestfulAPIs.entity.dbOrm.WeatherData;
import com.glenn.weatherRestfulAPIs.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author Liu Jialin
 * @Date 2025/3/27 15:50
 * @PackageName com.glenn.weatherRestfulAPIs.service.Impl
 * @ClassName WeatherServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    private static final int MAX_REQUESTS_PER_HOUR = 5;
    private ApiKeyRepository apiKeyRepository;
    private WeatherDataRepository weatherDataRepository;
    @Autowired
    public WeatherServiceImpl(ApiKeyRepository apiKeyRepository, WeatherDataRepository weatherDataRepository){
        this.apiKeyRepository = apiKeyRepository;
        this.weatherDataRepository = weatherDataRepository;
    }

    @Override
    public String requestForWeatherData(String city, String country, String apiKey) {
        if(!checkAndUpdateApiKey(apiKey)){
            throw new RuntimeException("ApiKey exceed query limit!");
        }
        //Full url
        String url = String.format("https://samples.openweathermap.org/data/2.5/weather?q=%s,%s&appid=%s", city, country, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject jsonResponse = JSONObject.parseObject(response.getBody());
        // Extract the "description" field from the weather array
        String description = jsonResponse.getJSONArray("weather")
                .getJSONObject(0)
                .getString("description");
        //TODO exception handling
        WeatherData weatherData = new WeatherData(city, country, description);
        weatherDataRepository.save(weatherData);
        return description;
    }

    public boolean checkAndUpdateApiKey(String apiKey) {
        // Check if API key exists
        Optional<ApiKey> optionalApiKey = apiKeyRepository.findByKeyValue(apiKey);

        if (optionalApiKey.isEmpty()) {
            // If the API key doesn't exist, throw exception for controller.
            throw new RuntimeException("Api key is invalid;");
        }
        ApiKey apiKeyRecord = optionalApiKey.get();
        LocalDateTime now = LocalDateTime.now();

        // If the last reset was more than an hour ago, reset the counter
        if (apiKeyRecord.getLastReset() != null && apiKeyRecord.getLastReset().isBefore(LocalDateTime.now().minusHours(1))) {
            // Reset count to 1 for the new hour
            apiKeyRecord.setRequestCount(1);
            apiKeyRecord.setLastReset(now);
        } else {
            // Check if the count exceeds the limit
            if (apiKeyRecord.getRequestCount() >= MAX_REQUESTS_PER_HOUR) {
                // Exceeded the limit
                return false;
            }
            apiKeyRecord.setRequestCount(apiKeyRecord.getRequestCount() + 1); // Increment request count
        }

        apiKeyRepository.save(apiKeyRecord);
        // Allow the request
        return true;
    }
}

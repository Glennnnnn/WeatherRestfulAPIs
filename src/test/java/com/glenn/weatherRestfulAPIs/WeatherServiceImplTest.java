package com.glenn.weatherRestfulAPIs;

import com.glenn.weatherRestfulAPIs.dao.ApiKeyRepository;
import com.glenn.weatherRestfulAPIs.dao.WeatherDataRepository;
import com.glenn.weatherRestfulAPIs.entity.dbOrm.ApiKey;
import com.glenn.weatherRestfulAPIs.entity.dbOrm.WeatherData;
import com.glenn.weatherRestfulAPIs.service.Impl.WeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Liu Jialin
 * @Date 2025/3/27 17:14
 * @PackageName com.glenn.weatherRestfulAPIs
 * @ClassName WeatherServiceImplTest
 * @Description TODO
 * @Version 1.0.0
 */
public class WeatherServiceImplTest {
    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private RestTemplate restTemplate;

    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherService = new WeatherServiceImpl(apiKeyRepository, weatherDataRepository);
    }
    //test for extract description
    @Test
    void testRequestForWeatherData_Success() {
        // Arrange
        String city = "London";
        String country = "UK";
        String apiKey = "18e06f7692492a6533ddde2e07f026a6";
        String mockDescription = "light intensity drizzle";

        ApiKey apiKeyRecord = new ApiKey();
        apiKeyRecord.setKeyValue(apiKey);
        apiKeyRecord.setRequestCount(2);
        apiKeyRecord.setLastReset(LocalDateTime.now().minusMinutes(10));

        // Mock repository methods
        when(apiKeyRepository.findByKeyValue(apiKey)).thenReturn(Optional.of(apiKeyRecord));

        // Mock rest template response
        String weatherJson = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":280.32,\"pressure\":1012,\"humidity\":81,\"temp_min\":279.15,\"temp_max\":281.15},\"visibility\":10000,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":90},\"dt\":1485789600,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0103,\"country\":\"GB\",\"sunrise\":1485762037,\"sunset\":1485794875},\"id\":2643743,\"name\":\"London\",\"cod\":200}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(weatherJson);
        when(restTemplate.getForEntity(any(String.class), eq(String.class))).thenReturn(responseEntity);

        String description = weatherService.requestForWeatherData(city, country, apiKey);

        // Assert
        verify(weatherDataRepository).save(any());
        assert(description.equals(mockDescription));
    }
    //test for exceed limit
    @Test
    void testRequestForWeatherData_ExceededRequestLimit() {
        // Arrange
        String city = "London";
        String country = "UK";
        String apiKey = "18e06f7692492a6533ddde2e07f026a6";

        ApiKey apiKeyRecord = new ApiKey();
        apiKeyRecord.setKeyValue(apiKey);
        apiKeyRecord.setRequestCount(5);
        apiKeyRecord.setLastReset(LocalDateTime.now().minusMinutes(10));

        // Mock repository methods
        when(apiKeyRepository.findByKeyValue(apiKey)).thenReturn(Optional.of(apiKeyRecord));

        boolean result = weatherService.checkAndUpdateApiKey(apiKey);
        assert(!result); // Should return false since request count exceeds the limit
    }
    //test invalid key
    @Test
    void testRequestForWeatherData_InvalidApiKey() {
        // Arrange
        String city = "London";
        String country = "UK";
        String apiKey = "invalid-api-key";

        // Mock repository methods
        when(apiKeyRepository.findByKeyValue(apiKey)).thenReturn(Optional.empty());

        // Act & Assert
        try {
            weatherService.requestForWeatherData(city, country, apiKey);
        } catch (RuntimeException e) {
            assert(e.getMessage().equals("Api key is invalid;"));
        }
    }
    //Test for no key
    @Test
    void testRequestForWeatherData_NullApiKey() {
        // Arrange
        String city = "London";
        String country = "UK";
        String apiKey = null;

        // Act & Assert
        try {
            weatherService.requestForWeatherData(city, country, apiKey);
        } catch (RuntimeException e) {
            assert(e.getMessage().equals("Api key is invalid;"));
        }
    }
}

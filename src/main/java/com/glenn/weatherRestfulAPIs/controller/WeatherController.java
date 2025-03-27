package com.glenn.weatherRestfulAPIs.controller;

import com.glenn.weatherRestfulAPIs.entity.reponse.ResponseDto;
import com.glenn.weatherRestfulAPIs.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Liu Jialin
 * @Date 2025/3/27 15:48
 * @PackageName com.glenn.weatherRestfulAPIs.controller
 * @ClassName WeatherController
 * @Description WeatherController
 * @Version 1.0.0
 */
@RestController
public class WeatherController {
    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    @GetMapping("/weatherData")
    public ResponseDto queryWeatherData(@RequestParam(required = false) String city,
                                        @RequestParam(required = false) String country,
                                        @RequestParam(required = false) String apiKey){
        ResponseDto responseDto = new ResponseDto();
        // Check if any parameter is missing
        if (city == null || city.isEmpty()) {
            return new ResponseDto(400, "City parameter is missing.");
        }

        if (country == null || country.isEmpty()) {
            return new ResponseDto(400, "Country parameter is missing.");
        }

        if (apiKey == null || apiKey.isEmpty()) {
            return new ResponseDto(400, "API Key parameter is missing.");
        }
        String result = null;
        try{
            result = weatherService.requestForWeatherData(city, country, apiKey);
        }catch (Exception e){
            responseDto.setCode(400);
            responseDto.setMsg(e.getMessage());
            responseDto.setData(null);
            return responseDto;
        }

        responseDto.setCode(200);
        responseDto.setMsg("Success");
        responseDto.setData(result);
        return responseDto;
    }

}

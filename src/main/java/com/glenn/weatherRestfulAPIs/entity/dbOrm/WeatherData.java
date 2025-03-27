package com.glenn.weatherRestfulAPIs.entity.dbOrm;
import jakarta.persistence.*;
/**
 * @Author Liu Jialin
 * @Date 2025/3/27 17:05
 * @PackageName com.glenn.weatherRestfulAPIs.entity.dbOrm
 * @ClassName WeatherData
 * @Description TODO
 * @Version 1.0.0
 */
@Entity
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String description;

    // Constructors
    public WeatherData() {}

    public WeatherData(String city, String country, String description) {
        this.city = city;
        this.country = country;
        this.description = description;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


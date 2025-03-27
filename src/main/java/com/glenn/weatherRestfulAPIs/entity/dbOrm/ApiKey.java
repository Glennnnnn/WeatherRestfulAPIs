package com.glenn.weatherRestfulAPIs.entity.dbOrm;
import jakarta.persistence.*;

import java.time.LocalDateTime;


/**
 * @Author Liu Jialin
 * @Date 2025/3/27 15:55
 * @PackageName com.glenn.weatherRestfulAPIs.entity.dbOrm
 * @ClassName ApiKey
 * @Description TODO
 * @Version 1.0.0
 */
@Entity
@Table(name = "api_keys")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String keyValue;

    private int requestCount;

    private LocalDateTime lastReset;

    public ApiKey() {}

    public ApiKey(String keyValue) {
        this.keyValue = keyValue;
        this.requestCount = 0;
        this.lastReset = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getKeyValue() { return keyValue; }
    public int getRequestCount() { return requestCount; }
    public LocalDateTime getLastReset() { return lastReset; }

    public void setKeyValue(String keyValue){this.keyValue = keyValue;}
    public void setRequestCount(int requestCount) { this.requestCount = requestCount; }
    public void setLastReset(LocalDateTime lastReset) { this.lastReset = lastReset; }
}

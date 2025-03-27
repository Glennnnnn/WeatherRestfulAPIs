package com.glenn.weatherRestfulAPIs.dao;

import com.glenn.weatherRestfulAPIs.entity.dbOrm.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Liu Jialin
 * @Date 2025/3/27 17:07
 * @PackageName com.glenn.weatherRestfulAPIs.dao
 * @ClassName WeatherDataRepository
 * @Description TODO
 * @Version 1.0.0
 */
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
}

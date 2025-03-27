package com.glenn.weatherRestfulAPIs.dao;

import com.glenn.weatherRestfulAPIs.entity.dbOrm.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author Liu Jialin
 * @Date 2025/3/27 15:59
 * @PackageName com.glenn.weatherRestfulAPIs.dao
 * @ClassName ApiKeyRepository
 * @Description H2 repository
 * @Version 1.0.0
 */
@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> findByKeyValue(String key);
}

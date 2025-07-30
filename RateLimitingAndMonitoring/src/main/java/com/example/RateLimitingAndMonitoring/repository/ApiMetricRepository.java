package com.example.RateLimitingAndMonitoring.repository;

import com.example.RateLimitingAndMonitoring.entity.ApiMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ApiMetricRepository extends JpaRepository<ApiMetric, Long> {
    Integer countByEndpoint(String endpoint);

    boolean existsByEndpoint(String endpoint);

    @Query(
            "select m.durationInMillis from ApiMetric as m where m.endpoint = :endpoint"
    )
    List<Long> getDurationsByEndpoint(@Param("endpoint") String endpoint);

    @Query(
            "select avg(case when m.success = true then 1 else 0 end) " +
                    "from ApiMetric m where m.endpoint = :endpoint"
    )
    BigDecimal averageSuccessRateByEndpoint(@Param("endpoint") String endpoint);

    @Query(
            "select avg(m.durationInMillis) from ApiMetric as m where m.endpoint = :endpoint"
    )
    Long getAverageDurationByEndpoint(@Param("endpoint") String endpoint);
}

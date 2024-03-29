package com.earlmazip.repository;

import com.earlmazip.domain.ApiCallStat;
import com.earlmazip.domain.ApiCallStatDetail;
import com.earlmazip.domain.AptInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApiStatisticsRepository extends JpaRepository<ApiCallStat, Long> {

    @Query("select a from ApiCallStat a where a.callDate = :callDate and a.apiGubn <> 'MENU' and a.apiGubn <> 'SIGUNGU' and a.apiGubn <> 'ERROR' order by a.cnt desc")
    List<ApiCallStat> findAllToday(@Param("callDate") String callDate);

    @Query("select a from ApiCallStat a where a.callDate = :callDate and a.apiGubn = :apiGubn order by a.cnt desc")
    List<ApiCallStat> findGubnToday(@Param("callDate") String callDate, @Param("apiGubn") String apiGubn);

    @Query("select a from ApiCallStatDetail a where a.callDate = :callDate order by a.cnt desc")
    List<ApiCallStatDetail> findGubnTodayDetail(@Param("callDate") String callDate);

    @Query(value = "select a.sigunguName, count(1) from ApiCallStatDetail a where a.callDate = :callDate", nativeQuery = true)
    List<ApiCallStatDetail> findTodaySigungu(@Param("callDate") String callDate);

    @Query(value = "select a from ApiCallStat a where a.callDate = :callDate and apiGubn = 'ERROR' ")
    List<ApiCallStat> findTodayError(@Param("callDate") String callDate);
}

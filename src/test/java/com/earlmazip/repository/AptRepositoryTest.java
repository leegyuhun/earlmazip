package com.earlmazip.repository;

import com.earlmazip.domain.ApiCallStat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AptRepositoryTest {

    @Autowired
    ApiStatisticsRepository apiStatisticsRepository;

    @Test
    void findAllStat() {
//        List<ApiCallStat> allToday = apiStatisticsRepository.findAllToday("20220926");
//
//        assertThat(allToday.stream().count()).isGreaterThan(0);
    }
}
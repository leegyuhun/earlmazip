package com.earlmazip.repository;

import com.earlmazip.domain.ApiCallStat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiStatisticsRepositoryTest {

    @Autowired
    ApiStatisticsRepository apiStatisticsRepository;

    @Test
    void findAllToday() {
        List<ApiCallStat> allToday = apiStatisticsRepository.findAllToday("20220926");

        assertThat(allToday.stream().count()).isGreaterThan(0);
    }

    @Test
    void findGubnToday() {
        List<ApiCallStat> allToday = apiStatisticsRepository.findGubnToday("20220926", "STAT_RANK_UA");

        assertThat(allToday.stream().count()).isGreaterThan(0);
    }
}
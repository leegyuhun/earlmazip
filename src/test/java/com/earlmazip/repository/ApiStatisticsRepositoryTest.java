package com.earlmazip.repository;

import com.earlmazip.controller.IpInfoController;
import com.earlmazip.domain.ApiCallStat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiStatisticsRepositoryTest {

    @Autowired
    ApiStatisticsRepository apiStatisticsRepository;

    @Autowired
    IpInfoController ipInfoController;

    @Test
    void findAllToday() {
//        List<ApiCallStat> allToday = apiStatisticsRepository.findAllToday("20220926");
//
//        assertThat(allToday.stream().count()).isGreaterThan(0);
    }

    @Test
    void findGubnToday() {
//        List<ApiCallStat> allToday = apiStatisticsRepository.findGubnToday("20220926", "STAT_RANK_UA");
//
//        assertThat(allToday.stream().count()).isGreaterThan(0);
    }

    @Test
    void test() throws IOException {
//        ipInfoController.MergeIpInformation("10.179.4.117");
    }
}
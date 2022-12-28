package com.earlmazip.service;

import com.earlmazip.domain.ApiCallStat;
import com.earlmazip.domain.ApiCallStatDetail;
import com.earlmazip.repository.ApiCallStatRepository;
import com.earlmazip.repository.ApiStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiCallStatService {

    private final ApiCallStatRepository apiCallStatRepository;

    private final ApiStatisticsRepository apiStatisticsRepository;

    @Transactional
    public void writeApiCallStat(String gubn, String name, String code) {
        if(code.equals("")) return;
        apiCallStatRepository.WriteApiCallStat(gubn, name, code);
    }

    @Transactional
    public void writeApiCallStatDetail(String apiName, String sigunguCode, String sigunguName) {
        if(apiName.equals("") || sigunguCode.equals("")) return;
        apiCallStatRepository.WriteApiCallStatDetail(apiName, sigunguCode, sigunguName);
    }

    public List<ApiCallStatDetail> findTodaySigungu(String date) {
        return apiStatisticsRepository.findTodaySigungu(date);
    }

    public List<ApiCallStat> LoadTodayApiCallList(String gubn) {
        return apiCallStatRepository.LoadTodayApiCallList(gubn);
    }

    public List<ApiCallStatDetail> findAllTodayDetail(String date) {
        return apiStatisticsRepository.findGubnTodayDetail(date);
    }



    public List<ApiCallStat> findAllToday(String date) {
        return apiStatisticsRepository.findAllToday(date);
    }

    public List<ApiCallStat> findGubnToday(String date, String gubn) {
        return apiStatisticsRepository.findGubnToday(date, gubn);
    }

    public List<ApiCallStat> findTodayError(String date) {
        return apiStatisticsRepository.findTodayError(date);
    }

}

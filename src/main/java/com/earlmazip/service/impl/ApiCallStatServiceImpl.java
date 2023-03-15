package com.earlmazip.service.impl;

import com.earlmazip.domain.ApiCallStat;
import com.earlmazip.domain.ApiCallStatDetail;
import com.earlmazip.repository.ApiCallStatRepository;
import com.earlmazip.repository.ApiStatisticsRepository;
import com.earlmazip.service.ApiCallStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
//public class ApiCallStatServiceImpl implements ApiCallStatService {
public class ApiCallStatServiceImpl {
//    private final ApiCallStatRepository apiCallStatRepository;
//    private final ApiStatisticsRepository apiStatisticsRepository;

//    ApiCallStatRepository apiCallStatRepository;
//
//    ApiStatisticsRepository apiStatisticsRepository;
//
//    @Autowired
//    public ApiCallStatServiceImpl(ApiCallStatRepository apiCallStatRepository, ApiStatisticsRepository apiStatisticsRepository) {
//        this.apiCallStatRepository = apiCallStatRepository;
//        this.apiStatisticsRepository = apiStatisticsRepository;
//    }
//
//    @Override
//    public void writeApiCallStat(String gubn, String name, String code) {
//        if(code.equals("")) return;
//        apiCallStatRepository.WriteApiCallStat(gubn, name, code);
//    }
//
//    @Override
//    public void writeApiCallStatDetail(String apiName, String sigunguCode, String sigunguName) {
//        if(apiName.equals("") || sigunguCode.equals("")) return;
//        apiCallStatRepository.WriteApiCallStatDetail(apiName, sigunguCode, sigunguName);
//    }
//
//    @Override
//    public List<ApiCallStatDetail> findTodaySigungu(String date) {
//        return apiStatisticsRepository.findTodaySigungu(date);
//    }
//
//    @Override
//    public List<ApiCallStat> LoadTodayApiCallList(String gubn) {
//        return apiCallStatRepository.LoadTodayApiCallList(gubn);
//    }
//
//    @Override
//    public List<ApiCallStatDetail> findAllTodayDetail(String date) {
//        return apiStatisticsRepository.findGubnTodayDetail(date);
//    }
//
//    @Override
//    public List<ApiCallStat> findAllToday(String date) {
//        return apiStatisticsRepository.findAllToday(date);
//    }
//
//    @Override
//    public List<ApiCallStat> findGubnToday(String date, String gubn) {
//        return apiStatisticsRepository.findGubnToday(date, gubn);
//    }
//
//    @Override
//    public List<ApiCallStat> findTodayError(String date) {
//        return apiStatisticsRepository.findTodayError(date);
//    }
}

package com.earlmazip.service;

import com.earlmazip.controller.dto.StatLeaseAnalysisDto;

import java.util.List;

public interface LeaseAnalysisService {

    List<StatLeaseAnalysisDto> getLeaseAnalysisList(String gubnCode, int term);
}

package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseAnalysisDto;
import aptdata.earlmazip.domain.StatLeaseAnalysis;
import aptdata.earlmazip.repository.LeaseAnalysisRepository;
import aptdata.earlmazip.repository.LeaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaseAnalysisService {

    private final LeaseAnalysisRepository leaseAnalysisRepository;

    public List<StatLeaseAnalysisDto> getLeaseAnalysisList(String gubnCode)
    {
        return leaseAnalysisRepository.getLeaseAnalysisList(gubnCode);
    }
}

package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.RankYearResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.repository.StatLeaseRepository;
import aptdata.earlmazip.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatLeaseService {

    private final StatLeaseRepository statRepository;

    public List<StatLeaseResponseDto> statLeaseSido(String sidoCode) {
        return statRepository.statLeaseSido(sidoCode);
    }

    public List<StatLeaseResponseDto> statLeaseMonthlySido(String sidoCode) {
        return statRepository.statLeaseMonthlySido(sidoCode);
    }

    public List<StatLeaseResponseDto> getStatLeaseSigungu(String sigunguCode) {
        return statRepository.getStatLeaseSigungu(sigunguCode);
    }

    public List<StatLeaseResponseDto> getStatLeaseMonthlySigungu(String sigunguCode) {
        return statRepository.getStatLeaseMonthlySigungu(sigunguCode);
    }
}

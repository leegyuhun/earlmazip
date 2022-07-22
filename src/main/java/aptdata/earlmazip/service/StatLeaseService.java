package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.RankLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.repository.StatLeaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatLeaseService {

    private final StatLeaseRepository statRepository;

    public List<StatLeaseResponseDto> getStatLeaseList_Gyunggi(String sidoCode, int term) {
        return statRepository.getStatLeaseList_Gyunggi(sidoCode, term);
    }

    public List<StatLeaseResponseDto> statLeaseMonthlySido(String sidoCode) {
        return statRepository.statLeaseMonthlySido(sidoCode);
    }

    public List<StatLeaseResponseDto> getStatLeaseList_Seoul(String sigunguCode, int term) {
        return statRepository.getStatLeaseList_Seoul(sigunguCode, term);
    }

    public List<StatLeaseResponseDto> getStatLeaseList_Seoul84(String sigunguCode, int gubn, int term) {
        return statRepository.getStatLeaseList_Seoul84(sigunguCode, gubn, term);
    }

    public List<StatLeaseResponseDto> getStatLeaseMonthlySigungu(String sigunguCode) {
        return statRepository.getStatLeaseMonthlySigungu(sigunguCode);
    }

    public List<RankLeaseResponseDto> getTopLeaseSigungu(String sigunguCode, String uaType, int leaseType) {
        return statRepository.getTopLeaseSigungu(sigunguCode, uaType, leaseType);

    }
}

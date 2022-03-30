package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.repository.LeaseRepository;
import aptdata.earlmazip.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaseService {

    private final LeaseRepository leaseRepository;

    public List<AptLeaseResponseDto> getLeaseList_SeoulSigungu(String sigungucode)
    {
        return leaseRepository.getLeaseList_SeoulSigungu(sigungucode);
    }

    public List<AptLeaseResponseDto> getLeaseList_GyunggiSido(String sidocode) {
        return leaseRepository.getLeaseList_GyunggiSido(sidocode);
    }

    public List<AptLeaseResponseDto> getLeaseList_IncheonSigungu(String sigungu) {
        return leaseRepository.getLeaseList_IncheonSigungu(sigungu);
    }
}

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

    public List<AptLeaseResponseDto> getLeaseRenewalList_SeoulSigungu(String sigungucode)
    {
        return leaseRepository.getLeaseRenewalList_SeoulSigungu(sigungucode);
    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_SeoulSigungu(String sigungucode)
    {
        return leaseRepository.getLeaseMonthlyList_SeoulSigungu(sigungucode);
    }

    public List<AptLeaseResponseDto> getLeaseList_GyunggiSido(String sidocode) {
        return leaseRepository.getLeaseList_GyunggiSido(sidocode);
    }

    public List<AptLeaseResponseDto> getLeaseRenewalList_GyunggiSigungu(String sigungucode) {
        return leaseRepository.getLeaseRenewalList_GyunggiSigungu(sigungucode);
    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_GyunggiSido(String sidocode) {
        return leaseRepository.getLeaseMonthlyList_GyunggiSido(sidocode);
    }

    public List<AptLeaseResponseDto> getLeaseList_IncheonSigungu(String sigungu) {
        return leaseRepository.getLeaseList_IncheonSigungu(sigungu);
    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_IncheonSigungu(String sigungu) {
        return leaseRepository.getLeaseMonthlyList_IncheonSigungu(sigungu);
    }

    public List<AptLeaseResponseDto> getLeaseList_ByName(String regncode, String dong, String aptName, int ua, int term) {
        return leaseRepository.getLeaseList_ByName(regncode, dong, aptName, ua, term);
    }
}

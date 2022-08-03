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

    public List<AptLeaseResponseDto> getLeaseList_Sigungu(String sigungucode, int gubn, int ua)
    {
        return leaseRepository.getLeaseList_Sigungu(sigungucode, gubn, ua);
    }

    public List<AptLeaseResponseDto> getLeaseList_SigunguUAType(String sigungucode, String uaType)
    {
        return leaseRepository.getLeaseList_SigunguUAType(sigungucode, uaType);
    }

    public List<AptLeaseResponseDto> getLeaseRenewalList_SeoulSigungu(String sigungucode)
    {
        return leaseRepository.getLeaseRenewalList_SeoulSigungu(sigungucode);
    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_SigunguUAType(String sigunguCode, String uaType)
    {
        return leaseRepository.getLeaseMonthlyList_SigunguUAType(sigunguCode, uaType);
    }

    public List<AptLeaseResponseDto> getLeaseRenewalList_GyunggiSigungu(String sigungucode) {
        return leaseRepository.getLeaseRenewalList_GyunggiSigungu(sigungucode);
    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_IncheonSigungu(String sigungu) {
        return leaseRepository.getLeaseMonthlyList_IncheonSigungu(sigungu);
    }

    public List<AptLeaseResponseDto> getLeaseList_ByName(String regncode, String dong, String aptName, int ua, int term) {
        return leaseRepository.getLeaseList_ByName(regncode, dong, aptName, ua, term);
    }

    public List<AptLeaseResponseDto> getMonthlyList_ByName(String regncode, String dong, String aptName, int ua, int term) {
        return leaseRepository.getMonthlyList_ByName(regncode, dong, aptName, ua, term);
    }

}

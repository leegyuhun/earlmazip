package aptdata.earlmazip.service;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    public List<AptPriceResponseDto> getTradeList_Sigungu(String sigungucode, int gubn, int ua)
    {
        return tradeRepository.getTradeList_Sigungu(sigungucode, gubn, ua);
    }

    public List<AptPriceResponseDto> getTradeList_GyunggiSido(String sidocode, int gubn, int ua) {
        return tradeRepository.getTradeList_GyunggiSido(sidocode, gubn, ua);
    }

    public List<AptPriceResponseDto> getTradeList_Incheon(String sigungu) {
        return tradeRepository.getTradeList_Incheon(sigungu);
    }

    public List<AptPriceResponseDto> getCancelDealList(String regncode) {
        return tradeRepository.getCancelDealList(regncode);
    }

    public List<AptPriceResponseDto> getAptTradeList_ByName(String regncode, String aptName, int ua, int term) {
        return tradeRepository.getAptTradeList_ByName(regncode, aptName, ua, term);
    }
}

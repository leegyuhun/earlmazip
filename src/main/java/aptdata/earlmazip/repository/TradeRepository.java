package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.domain.AptPriceRaw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TradeRepository {

    private final EntityManager em;

    public List<AptPriceResponseDto> getTradeList_SeoulSigungu(String sigungucode) {
        return em.createQuery("select a from AptPriceRaw a"
                        + " where a.dealYear >= 2021 and a.areaCode = '11' and a.sigunguCode = :sigunguCode"
                        + " order by a.dealDate desc", AptPriceRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeList_GyunggiSido(String sidocode) {
        return em.createQuery("select a from AptPriceRaw a"
                        + " where a.dealYear >= 2021 and a.areaCode = '41' and a.sidoCode = :sidoCode"
                        + " order by a.dealDate desc", AptPriceRaw.class)
                .setParameter("sidoCode", sidocode)
                .setMaxResults(100)
                .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

}

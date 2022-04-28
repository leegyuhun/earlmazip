package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.domain.AptPriceRaw;
import aptdata.earlmazip.domain.CancelDealData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        if (sidocode.length() == 4) {
            return em.createQuery("select a from AptPriceRaw a"
                            + " where a.dealYear >= 2021 and a.areaCode = '41' and a.sidoCode = :sidoCode"
                            + " order by a.dealDate desc", AptPriceRaw.class)
                    .setParameter("sidoCode", sidocode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery("select a from AptPriceRaw a"
                            + " where a.dealYear >= 2021 and a.areaCode = '41' and a.sigunguCode = :sigunguCode"
                            + " order by a.dealDate desc", AptPriceRaw.class)
                    .setParameter("sigunguCode", sidocode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<AptPriceResponseDto> getTradeList_Incheon(String sigungucode) {
        return em.createQuery("select a from AptPriceRaw a"
                        + " where a.dealYear >= 2021 and a.areaCode = '28' and a.sigunguCode = :sigunguCode"
                        + " order by a.dealDate desc", AptPriceRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getCancelDealList(String regncode) {
        if (regncode.length() == 5) {
            return em.createQuery(" select a from CancelDealData a "
                            + " where  a.dealYear >= 2021 and a.sigunguCode = :regncode "
                            + "   and a.cnclDealDate <> '' "
                            + " order by a.cnclDealDate desc", CancelDealData.class)
                    .setParameter("regncode", regncode)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());

        } else {
            return em.createQuery(" select a from CancelDealData a "
                            + " where  a.dealYear >= 2021 and a.sidoCode = :regncode "
                            + "   and a.cnclDealDate <> '' "
                            + " order by a.cnclDealDate desc", CancelDealData.class)
                    .setParameter("regncode", regncode)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<AptPriceResponseDto> getAptTradeList_ByName(String regnCode, String aptName, int ua, int term) {
        if (regnCode.length() == 5) {
            if (ua == 0) {
                return em.createQuery(" select a from AptPriceRaw a "
                                + " where a.sigunguCode = :regncode "
                                + "   and a.dealYear >= :searchYear "
                                + "   and a.aptName = :aptname "
                                + "   and a.cnclDealDate = '' "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("regncode", regnCode)
                        .setParameter("searchYear", calcYearByTerm(term))
                        .setParameter("aptname", aptName)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            } else {
                return em.createQuery(" select a from AptPriceRaw a "
                                + " where a.sigunguCode = :regncode "
                                + "   and a.dealYear >= :searchYear "
                                + "   and a.aptName = :aptname "
                                + "   and a.useAreaTrunc = :ua "
                                + "   and a.cnclDealDate = '' "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("regncode", regnCode)
                        .setParameter("searchYear", calcYearByTerm(term))
                        .setParameter("aptname", aptName)
                        .setParameter("ua", ua)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            }

        } else {
            if (ua == 0) {
                return em.createQuery(" select a from AptPriceRaw a "
                                + " where a.sidoCode = :regncode "
                                + "   and a.dealYear >= :searchYear "
                                + "   and a.aptName = :aptname "
                                + "   and a.cnclDealDate = '' "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("regncode", regnCode)
                        .setParameter("searchYear", calcYearByTerm(term))
                        .setParameter("aptname", aptName)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            } else {
                return em.createQuery(" select a from AptPriceRaw a "
                                + " where a.sidoCode = :regncode "
                                + "   and a.dealYear >= :searchYear "
                                + "   and a.aptName = :aptname "
                                + "   and a.useAreaTrunc = :ua "
                                + "   and a.cnclDealDate = '' "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("regncode", regnCode)
                        .setParameter("searchYear", calcYearByTerm(term))
                        .setParameter("aptname", aptName)
                        .setParameter("ua", ua)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            }
        }
    }
    private String calcYearByTerm(int term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        // 현재날짜-term = 조회 기준일자
        return Integer.toString(nowInt - term);
    }
}

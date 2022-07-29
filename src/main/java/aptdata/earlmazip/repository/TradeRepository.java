package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.domain.AptDistributionRaw;
import aptdata.earlmazip.domain.AptPriceRaw;
import aptdata.earlmazip.domain.CancelDealData;
import aptdata.earlmazip.utils.Common;
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

    public List<AptPriceResponseDto> getTradeList_SigunguUAType(String sigungucode, String uaType) {
        if (uaType.equals("UA01")) {
            return em.createQuery("select a from AptPriceRaw a"
                            + " where a.dealYear >= 2021 and a.sigunguCode = :sigunguCode "
                            + " order by a.dealDate desc", AptPriceRaw.class)
                    .setParameter("sigunguCode", sigungucode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery("select a from AptPriceRaw a"
                            + " where a.dealYear >= 2021 and a.sigunguCode = :sigunguCode "
                            + "   and a.useAreaType = :uaType "
                            + " order by a.dealDate desc", AptPriceRaw.class)
                    .setParameter("sigunguCode", sigungucode)
                    .setParameter("uaType", uaType)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        }

    }

    public List<AptPriceResponseDto> getTradeComparePrevList_SigunguUAType(String sigungucode, String type, String uaType) {
        if (type.equals("0")) {
            if (uaType.equals("UA01")) {
                return em.createQuery("select a from AptPriceRaw a"
                                + " where a.dealYear = 2022 and a.sigunguCode = :sigunguCode "
                                + "   and a.prevDealAmt < a.dealAmt "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("sigunguCode", sigungucode)
                        .setMaxResults(100)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            } else {
                return em.createQuery("select a from AptPriceRaw a"
                                + " where a.dealYear = 2022 and a.sigunguCode = :sigunguCode "
                                + "   and a.useAreaType = :uaType "
                                + "   and a.prevDealAmt < a.dealAmt "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("sigunguCode", sigungucode)
                        .setParameter("uaType", uaType)
                        .setMaxResults(100)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            }
        } else {
            if (uaType.equals("UA01")) {
                return em.createQuery("select a from AptPriceRaw a"
                                + " where a.dealYear = 2022 and a.sigunguCode = :sigunguCode "
                                + "   and a.prevDealAmt > a.dealAmt "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("sigunguCode", sigungucode)
                        .setMaxResults(100)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            } else {
                return em.createQuery("select a from AptPriceRaw a"
                                + " where a.dealYear = 2022 and a.sigunguCode = :sigunguCode "
                                + "   and a.useAreaType = :uaType "
                                + "   and a.prevDealAmt > a.dealAmt "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("sigunguCode", sigungucode)
                        .setParameter("uaType", uaType)
                        .setMaxResults(100)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            }
        }
    }

    public List<AptPriceResponseDto> getTradeList_Sigungu(String sigungucode, int gubn, int ua) {
        if (ua == 0) {
            return em.createQuery("select a from AptPriceRaw a"
                            + " where a.dealYear >= 2021 and a.sigunguCode = :sigunguCode"
                            + " order by a.dealDate desc", AptPriceRaw.class)
                    .setParameter("sigunguCode", sigungucode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else {
            if (gubn == 1) {
                return em.createQuery("select a from AptPriceRaw a"
                                + " where a.dealYear >= 2021 and a.sigunguCode = :sigunguCode "
                                + "   and a.useAreaTrunc < :ua "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("sigunguCode", sigungucode)
                        .setParameter("ua", ua)
                        .setMaxResults(100)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            } else if (gubn == 2) {
                return em.createQuery("select a from AptPriceRaw a"
                                + " where a.dealYear >= 2021 and a.sigunguCode = :sigunguCode "
                                + "   and a.useAreaTrunc > :ua "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("sigunguCode", sigungucode)
                        .setParameter("ua", ua)
                        .setMaxResults(100)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            } else {
                return em.createQuery("select a from AptPriceRaw a"
                                + " where a.dealYear >= 2021 and a.sigunguCode = :sigunguCode "
                                + "   and a.useAreaTrunc = :ua "
                                + " order by a.dealDate desc", AptPriceRaw.class)
                        .setParameter("sigunguCode", sigungucode)
                        .setParameter("ua", ua)
                        .setMaxResults(100)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            }
        }
    }

    public List<AptPriceResponseDto> getTradeList_GyunggiSido(String sidocode, int gubn, int ua) {
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

    public List<AptPriceResponseDto> getAptTradeList_ByName(String regnCode, String landDong, String aptName, int ua, int term) {
        if (regnCode.length() == 5) {
            if (ua == 0) {
                if (landDong.equals("")) {
                    return em.createQuery(" select a from AptPriceRaw a "
                                    + " where a.sigunguCode = :regncode "
                                    + "   and a.dealYear >= :searchYear "
                                    + "   and a.aptName = :aptname "
                                    + "   and a.cnclDealDate = '' "
                                    + " order by a.dealDate desc", AptPriceRaw.class)
                            .setParameter("regncode", regnCode)
                            .setParameter("searchYear", Common.calcYearByTerm(term))
                            .setParameter("aptname", aptName)
                            .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
                } else {
                    return em.createQuery(" select a from AptPriceRaw a "
                                    + " where a.sigunguCode = :regncode "
                                    + "       a.landDong = :landDong "
                                    + "   and a.dealYear >= :searchYear "
                                    + "   and a.aptName = :aptname "
                                    + "   and a.cnclDealDate = '' "
                                    + " order by a.dealDate desc", AptPriceRaw.class)
                            .setParameter("regncode", regnCode)
                            .setParameter("landDong", landDong)
                            .setParameter("searchYear", Common.calcYearByTerm(term))
                            .setParameter("aptname", aptName)
                            .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
                }

            } else {
                if (landDong.equals("")) {
                    return em.createQuery(" select a from AptPriceRaw a "
                                    + " where a.sigunguCode = :regncode "
                                    + "   and a.dealYear >= :searchYear "
                                    + "   and a.aptName = :aptname "
                                    + "   and a.useAreaTrunc = :ua "
                                    + "   and a.cnclDealDate = '' "
                                    + " order by a.dealDate desc", AptPriceRaw.class)
                            .setParameter("regncode", regnCode)
                            .setParameter("searchYear", Common.calcYearByTerm(term))
                            .setParameter("aptname", aptName)
                            .setParameter("ua", ua)
                            .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
                } else {
                    return em.createQuery(" select a from AptPriceRaw a "
                                    + " where a.sigunguCode = :regncode "
                                    + "   and a.landDong = :landDong "
                                    + "   and a.dealYear >= :searchYear "
                                    + "   and a.aptName = :aptname "
                                    + "   and a.useAreaTrunc = :ua "
                                    + "   and a.cnclDealDate = '' "
                                    + " order by a.dealDate desc", AptPriceRaw.class)
                            .setParameter("regncode", regnCode)
                            .setParameter("landDong", landDong)
                            .setParameter("searchYear", Common.calcYearByTerm(term))
                            .setParameter("aptname", aptName)
                            .setParameter("ua", ua)
                            .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
                }
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
                        .setParameter("searchYear", Common.calcYearByTerm(term))
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
                        .setParameter("searchYear", Common.calcYearByTerm(term))
                        .setParameter("aptname", aptName)
                        .setParameter("ua", ua)
                        .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
            }
        }
    }

    public List<AptPriceResponseDto> getNewHighestList(String sigungucode, String uaType) {
        if (uaType.equals("UA01")) {
            return em.createQuery(" select a from AptPriceRaw a "
                            + " where a.sigunguCode = :sigungucode "
                            + "   and a.dealYear = 2022 "
                            + "   and a.cnclDealDate = '' "
                            + "   and a.newHighestPrice = 1 "
                            + " order by a.dealDate desc", AptPriceRaw.class)
                    .setParameter("sigungucode", sigungucode)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery(" select a from AptPriceRaw a "
                            + " where a.sigunguCode = :sigungucode "
                            + "   and a.dealYear = 2022 "
                            + "   and a.useAreaType = :uaType "
                            + "   and a.cnclDealDate = '' "
                            + "   and a.newHighestPrice = 1 "
                            + " order by a.dealDate desc", AptPriceRaw.class)
                    .setParameter("sigungucode", sigungucode)
                    .setParameter("uaType", uaType)
                    .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<AptPriceResponseDto> getTradeDistribution_BySigungu(String dealYear, String sigungucode) {
        return em.createQuery("select a from AptDistributionRaw a"
                        + " where a.dealYear = :dealYear and a.sigunguCode = :sigunguCode "
                        + " order by a.dealDate desc", AptDistributionRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setParameter("dealYear", dealYear)
                .setMaxResults(500)
                .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeDistribution_ByName(String dealYear, String sigungucode, String landDong, String aptName) {
        return em.createQuery("select a from AptDistributionRaw a"
                        + " where a.dealYear = :dealYear "
                        + "   and a.sigunguCode = :sigunguCode "
                        + "   and a.landDong = :landDong "
                        + "   and a.aptName = :aptName "
                        + " order by a.dealDate desc", AptDistributionRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setParameter("landDong", landDong)
                .setParameter("aptName", aptName)
                .setParameter("dealYear", dealYear)
                .setMaxResults(500)
                .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }
}

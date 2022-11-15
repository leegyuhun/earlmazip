package com.earlmazip.repository;

import com.earlmazip.controller.dto.*;
import com.earlmazip.domain.*;
import com.earlmazip.utils.Common;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StatRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StatRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StatResponseDto> getStatTradeList(String areaCode, String term){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = :areaCode and use_area_type = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("areaCode", areaCode)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeList_BySigungu(String sigunguCode, String term){
        return em.createQuery("select a from StatSigunguYYMM a"
                        + " where a.sigunguCode = :sigunguCode and use_area_type = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatSigunguYYMM.class)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .setParameter("sigunguCode", sigunguCode)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> findSeoulYear(String year){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + "   and a.dealYear = :dealYaer"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("dealYaer", year)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 평형별 매매가 통계 조회
     * @param regnCode: 지역코드
     * @param ua: UA01(전체), UA02(~59), UA03(59-85), UA04(85-102), UA05(102-135), UA06(135~)
     * @param term
     * @return
     */
    public List<StatResponseDto> getStatTradeByUseAreaList(String regnCode, String ua, String term){
        return em.createQuery("select a from StatSigunguYYMM a"
                        + " where a.sigunguCode = :regnCode and a.useAreaType = :useAreaType"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatSigunguYYMM.class)
                .setParameter("regnCode", regnCode)
                .setParameter("useAreaType", ua)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getStatTradeTopByYear(String year, String sigungucode, String uaType) {
        return em.createQuery(" select a from RankYear a "
                        + " where a.sigunguCode = :sigunguCode and a.dealYear = :dealYear"
                        + "   and a.useAreaType = :ua "
                        + " order by a.dealAmt desc", RankYear.class)
                .setParameter("sigunguCode", sigungucode)
                .setParameter("dealYear", year)
                .setParameter("ua", uaType)
                .setMaxResults(100)
                .getResultList().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeGyunggi(String term){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and useAreaType = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeList_ByCity(String sidoCode, String term){
        return em.createQuery("select a from StatSidoYYMM a"
                        + " where a.sidoCode = :sidoCode and use_area_type = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatSidoYYMM.class)
                .setParameter("sidoCode", sidoCode)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatNewHighestAndTradeCount(String sidoCode) {
        if (sidoCode.length() == 4) {
            return em.createQuery("select a from StatSidoYYMM a "
                            + " where a.sidoCode = :sidoCode and use_area_type = 'UA01' "
                            + " and deal_year > 2016 "
                            + " order by a.dealYYMM desc ", StatSidoYYMM.class)
                    .setParameter("sidoCode", sidoCode)
                    .getResultList().stream().map(StatResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            return em.createQuery("select a from StatAreaYYMM a "
                            + " where a.areaCode = :sidoCode and use_area_type = 'UA01' "
                            + " and deal_year > 2016 "
                            + " order by a.dealYYMM desc ", StatAreaYYMM.class)
                    .setParameter("sidoCode", sidoCode)
                    .getResultList().stream().map(StatResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    public List<StatResponseDto> getStatTheme(String themeCode, String term) {
        return em.createQuery("select a from StatTheme a "
                        + " where a.themeCode = :themeCode "
                        + "   and a.date >= :searchYear "
                        + " order by a.date desc ", StatTheme.class)
                .setParameter("themeCode", themeCode)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatBuildYearList(String regnCode, String buildYear, String term) {
        return em.createQuery("select a from StatBuildYear a "
                        + " where a.regnCode = :regnCode "
                        + "   and a.buildYear = :buildYear "
                        + "   and a.dealYear >= :searchYear "
                        + " order by a.dealYear desc", StatBuildYear.class)
                .setParameter("regnCode", regnCode)
                .setParameter("buildYear", buildYear)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<RankUaSigunguResponseDto> getStatRankUaList_Seoul(int rankGubn, String sigunguCode, int ua) {
        if (rankGubn == 0) {
            return em.createQuery("select a from RankUaSigungu a "
                            + " where a.sigunguCode = :sigunguCode "
                            + "   and a.rankGubn = :rankGubn "
                            + "   and a.useAreaTrunc = :ua "
                            + " order by a.avgAmt desc ", RankUaSigungu.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("rankGubn", rankGubn)
                    .setParameter("ua", ua)
                    .getResultList().stream().map(RankUaSigunguResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            return em.createQuery("select a from RankUaSigungu a "
                            + " where a.sigunguCode = :sigunguCode "
                            + "   and a.rankGubn = :rankGubn "
                            + "   and a.useAreaTrunc = :ua "
                            + " order by a.tradeCnt desc ", RankUaSigungu.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("rankGubn", rankGubn)
                    .setParameter("ua", ua)
                    .getResultList().stream().map(RankUaSigunguResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    public List<RankUaSigunguResponseDto> getStatRankUaTypeList(int rankGubn, int dealYear, String sigunguCode, String uaType) {
        if (rankGubn == 0) {
            return em.createQuery("select a from RankUatypeSigungu a "
                            + " where a.dealYear = :dealYear "
                            + "   and a.sigunguCode = :sigunguCode "
                            + "   and a.rankGubn = :rankGubn "
                            + "   and a.useAreaType = :uaType "
                            + " order by a.avgAmt desc ", RankUatypeSigungu.class)
                    .setParameter("dealYear", dealYear)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("rankGubn", rankGubn)
                    .setParameter("uaType", uaType)
                    .getResultList().stream().map(RankUaSigunguResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            return em.createQuery("select a from RankUatypeSigungu a "
                            + " where a.dealYear = :dealYear "
                            + "   and a.sigunguCode = :sigunguCode "
                            + "   and a.rankGubn = :rankGubn "
                            + "   and a.useAreaType = :uaType "
                            + " order by a.tradeCnt desc ", RankUatypeSigungu.class)
                    .setParameter("dealYear", dealYear)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("rankGubn", rankGubn)
                    .setParameter("uaType", uaType)
                    .getResultList().stream().map(RankUaSigunguResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    public List<StatResponseDto> getStatByDealType(String sigunguCode, String uaType, int dealType) {
        String mType = "중개거래";
        if (dealType == 0) {
            mType = "중개거래";
        } else {
            mType = "직거래";
        }

        return em.createQuery("select a from StatSigunguType a "
                        + " where a.sigunguCode = :sigunguCode "
                        + "   and a.useAreaType = :uaType "
                        + "   and a.dealType = :dealType "
                        + " order by dealYYMM desc", StatSigunguType.class)
                .setParameter("sigunguCode", sigunguCode)
                .setParameter("uaType", uaType)
                .setParameter("dealType", mType)
                .getResultList().stream().map(StatResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<StatResponseDto> getDistribution(String areaCode) {
        return em.createQuery("select a from StatDistribution a "
                        + " where a.areaCode = :areaCode "
                        + " order by sigunguName desc", StatDistribution.class)
                .setParameter("areaCode", areaCode)
                .getResultList().stream().map(StatResponseDto::new)
                .collect(Collectors.toList());
    }
        
}
    

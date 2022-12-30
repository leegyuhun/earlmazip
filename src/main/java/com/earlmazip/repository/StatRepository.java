package com.earlmazip.repository;

import com.earlmazip.controller.dto.*;
import com.earlmazip.domain.*;
import com.earlmazip.utils.Common;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class StatRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StatRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QStatSigunguYYMM qStatSigunguYYMM = QStatSigunguYYMM.statSigunguYYMM;

    QStatSigunguType qStatSigunguType = QStatSigunguType.statSigunguType;

    QRankUatypeSigungu qRankUatypeSigungu = QRankUatypeSigungu.rankUatypeSigungu;

    QRankYear qRankYear = QRankYear.rankYear;

    public List<StatSigunguYYMM> getStatTradeList(String areaCode, String term){
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(areaCode)) {
            builder.and(qStatSigunguYYMM.sigunguCode.eq(areaCode));
        }
        if (hasText(term)) {
            builder.and(qStatSigunguYYMM.dealYear.goe(Common.calcYearByTerm(term)));
        }
        builder.and(qStatSigunguYYMM.useAreaType.eq("UA01"));

        return queryFactory.selectFrom(qStatSigunguYYMM)
                .where(builder)
                .orderBy(qStatSigunguYYMM.dealYYMM.desc())
                .fetch();
    }

    /**
     * 평형별 매매가 통계 조회
     * @param sigunguCode: 지역코드
     * @param ua: UA01(전체), UA02(~59), UA03(59-85), UA04(85-102), UA05(102-135), UA06(135~)
     * @param term
     * @return
     */
    public List<StatSigunguYYMM> getStatTradeByUseAreaList(String sigunguCode, String ua, String term){

        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qStatSigunguYYMM.sigunguCode.eq(sigunguCode));
        }
        if (hasText(term)) {
            builder.and(qStatSigunguYYMM.dealYear.goe(Common.calcYearByTerm(term)));
        }
        builder.and(qStatSigunguYYMM.useAreaType.eq(ua));

        return queryFactory.selectFrom(qStatSigunguYYMM)
                .where(builder)
                .orderBy(qStatSigunguYYMM.dealYYMM.desc())
                .fetch();
    }

    public List<RankYear> getStatTradeTopByYear(String year, String sigungucode, String uaType) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRankYear.dealYear.eq(year));
        builder.and(qRankYear.sigunguCode.eq(sigungucode));
        builder.and(qRankYear.useAreaType.eq(uaType));

        return queryFactory.selectFrom(qRankYear)
                .where(builder)
                .orderBy(qRankYear.dealAmt.desc())
                .fetch();
    }

    public List<StatSigunguYYMM> getStatNewHighestAndTradeCount(String sigunguCode, String uaType, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qStatSigunguYYMM.sigunguCode.eq(sigunguCode));
        }
        builder.and(qStatSigunguYYMM.dealYear.goe(Common.calcYearByTerm(term)));
        builder.and(qStatSigunguYYMM.useAreaType.eq(uaType));

        return queryFactory.selectFrom(qStatSigunguYYMM)
                .where(builder)
                .orderBy(qStatSigunguYYMM.dealYYMM.desc())
                .fetch();
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

    public List<RankUaSigunguResponseDto> getStatRankUaTypeList(int rankGubn, int dealYear, String sigunguCode, String uaType) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qRankUatypeSigungu.rankGubn.eq(rankGubn));
        builder.and(qRankUatypeSigungu.dealYear.eq(dealYear));
        builder.and(qRankUatypeSigungu.sigunguCode.eq(sigunguCode));
        builder.and(qRankUatypeSigungu.useAreaType.eq(uaType));
        if (rankGubn == 0) {
            return queryFactory.selectFrom(qRankUatypeSigungu)
                    .where(builder)
                    .orderBy(qRankUatypeSigungu.avgAmt.desc())
                    .fetch().stream().map(RankUaSigunguResponseDto::new).collect(Collectors.toList());
        }else{
            return queryFactory.selectFrom(qRankUatypeSigungu)
                    .where(builder)
                    .orderBy(qRankUatypeSigungu.tradeCnt.desc())
                    .fetch().stream().map(RankUaSigunguResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<StatSigunguType> getStatByDealType(String sigunguCode, String uaType, int dealType) {

        BooleanBuilder builder = new BooleanBuilder();

        if (hasText(sigunguCode)) {
            builder.and(qStatSigunguType.sigunguCode.eq(sigunguCode));
        }
        if (hasText(uaType)) {
            builder.and(qStatSigunguType.useAreaType.eq(uaType));
        }
        if (dealType == 0) {
            builder.and(qStatSigunguType.dealType.eq("중개거래"));
        } else {
            builder.and(qStatSigunguType.dealType.eq("직거래"));
        }

        return queryFactory.selectFrom(qStatSigunguType)
                .where(builder)
                .orderBy(qStatSigunguType.dealYYMM.desc())
                .fetch();

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
    

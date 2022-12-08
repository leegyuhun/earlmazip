package com.earlmazip.repository;

import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.domain.*;
import com.earlmazip.utils.Common;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class TradeRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QAptPriceRaw qAptPriceRaw = QAptPriceRaw.aptPriceRaw;
    QCancelDealData qCancelDealData = QCancelDealData.cancelDealData;
    QAptDistributionRaw qAptDistributionRaw = QAptDistributionRaw.aptDistributionRaw;

    public TradeRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 최근200 매매내역 조회
     * @param cond
     * @return
     */
    public List<AptPriceRaw> findTradeList(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qAptPriceRaw.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qAptPriceRaw.sigunguCode.eq(cond.getSigunguCode()));
            }
        }
        if (hasText(cond.getDealYear())) {
            builder.and(qAptPriceRaw.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qAptPriceRaw.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceRaw.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceRaw.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceRaw)
                .where(builder)
                .orderBy(qAptPriceRaw.dealDate.desc())
                .limit(200)
                .fetch();
    }

    /**
     * 상승/하락 매매거래내역 거래
     * @param cond
     * @param type
     * @return
     */
    public List<AptPriceRaw> findTradeComparePrevList(TradeSearchCond cond, String type) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceRaw.sigunguCode.eq(cond.getSigunguCode()));
        }

        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceRaw.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceRaw.landDong.eq(cond.getLandDong()));
        }

        if (type.equals("0")) {
            builder.and(qAptPriceRaw.prevDealAmt.lt(qAptPriceRaw.dealAmt));
        } else {
            builder.and(qAptPriceRaw.prevDealAmt.gt(qAptPriceRaw.dealAmt));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        builder.and(qAptPriceRaw.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceRaw.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceRaw)
                .where(builder)
                .orderBy(qAptPriceRaw.dealDate.desc())
                .fetch();
    }

    /**
     * 거래취소건 조회
     * @param sigunguCode
     * @return
     */
    public List<CancelDealData> findCancelDealList(String sigunguCode) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qCancelDealData.sigunguCode.eq(sigunguCode));
        }
        return queryFactory.selectFrom(qCancelDealData)
                .where(builder)
                .orderBy(qCancelDealData.cnclDealDate.desc())
                .fetch();
    }

    /**
     * 아파트 거래내역 조회
     * @param cond
     * @param term
     * @return
     */
    public List<AptPriceRaw> findAptTradeList(TradeSearchCond cond, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceRaw.sigunguCode.eq(cond.getSigunguCode()));
        }
        builder.and(qAptPriceRaw.dealYear.goe(Common.calcYearByTerm(term)));
        if (hasText(cond.getAptName())) {
            builder.and(qAptPriceRaw.aptName.eq(cond.getAptName()));
        }
        if (cond.getUseAreaTrunc() != 0) {
            builder.and(qAptPriceRaw.useAreaTrunc.eq(cond.getUseAreaTrunc()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceRaw.landDong.eq(cond.getLandDong()));
        }
        builder.and(qAptPriceRaw.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceRaw)
                .where(builder)
                .orderBy(qAptPriceRaw.dealDate.desc())
                .fetch();
    }

    public List<AptPriceRaw> findNewHighestList(String sigungucode, String uaType, String landDong) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigungucode)) {
            builder.and(qAptPriceRaw.sigunguCode.eq(sigungucode));
        }
        if (hasText(uaType)) {
            if (!uaType.equals("UA01")) {
                builder.and(qAptPriceRaw.useAreaType.eq(uaType));
            }
        }
        if (hasText(landDong)) {
            builder.and(qAptPriceRaw.landDong.eq(landDong));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        builder.and(qAptPriceRaw.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceRaw.newHighestPrice.eq(1));
        builder.and(qAptPriceRaw.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceRaw)
                .where(builder)
                .orderBy(qAptPriceRaw.dealDate.desc())
                .fetch();
    }

    public List<AptDistributionRaw> findTradeDistribution_BySigungu(String dealYear, String sigunguCode) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qAptDistributionRaw.sigunguCode.eq(sigunguCode));
        }
        if (hasText(dealYear)) {
            builder.and(qAptDistributionRaw.dealYear.eq(dealYear));
        }

        return queryFactory.selectFrom(qAptDistributionRaw)
                .where(builder)
                .orderBy(qAptDistributionRaw.dealDate.desc())
                .limit(1000)
                .fetch();
    }

    public List<AptDistributionRaw> getTradeDistribution_ByName(String dealYear, String sigunguCode, String landDong, String aptName) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(dealYear)) {
            builder.and(qAptDistributionRaw.dealYear.eq(dealYear));
        }
        if (hasText(sigunguCode)) {
            builder.and(qAptDistributionRaw.sigunguCode.eq(sigunguCode));
        }
        if (hasText(landDong)) {
            builder.and(qAptDistributionRaw.landDong.eq(landDong));
        }
        if (hasText(aptName)) {
            builder.and(qAptDistributionRaw.aptName.eq(aptName));
        }
        return queryFactory.selectFrom(qAptDistributionRaw)
                .where(builder)
                .limit(1000)
                .fetch();
    }
}

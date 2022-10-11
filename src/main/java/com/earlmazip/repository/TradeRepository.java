package com.earlmazip.repository;

import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.domain.AptDistributionRaw;
import com.earlmazip.domain.AptPriceRaw;
import com.earlmazip.domain.CancelDealData;
import com.earlmazip.domain.QAptPriceRaw;
import com.earlmazip.utils.Common;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class TradeRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QAptPriceRaw qAptPriceRaw = QAptPriceRaw.aptPriceRaw;

    public TradeRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 최근100 매매내역 조회
     * @param cond
     * @return
     */
    public List<AptPriceRaw> findTradeList(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceRaw.sigunguCode.eq(cond.getSigunguCode()));
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
                .limit(100)
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
        if (hasText(cond.getDealYear())) {
            builder.and(qAptPriceRaw.dealYear.goe(cond.getDealYear()));
        }
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

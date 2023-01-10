package com.earlmazip.repository;

import com.earlmazip.controller.dto.AptPriceResponseDto;
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
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class TradeRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QAptPriceRaw qAptPriceRaw = QAptPriceRaw.aptPriceRaw;
    QAptPriceGs qAptPriceGs = QAptPriceGs.aptPriceGs;
    QAptPriceGw qAptPriceGw = QAptPriceGw.aptPriceGw;
    QAptPriceCc qAptPriceCc = QAptPriceCc.aptPriceCc;
    QAptPriceJl qAptPriceJl = QAptPriceJl.aptPriceJl;
    QAptPriceJj qAptPriceJj = QAptPriceJj.aptPriceJj;
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
    public List<AptPriceResponseDto> findTradeList(TradeSearchCond cond) {
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
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findTradeList_GS(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qAptPriceGs.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qAptPriceGs.sigunguCode.eq(cond.getSigunguCode()));
            }
        }

        if (hasText(cond.getDealYear())) {
            builder.and(qAptPriceGs.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qAptPriceGs.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceGs.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceGs.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceGs)
                .where(builder)
                .orderBy(qAptPriceGs.dealDate.desc())
                .limit(200)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findTradeList_GW(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qAptPriceGw.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qAptPriceGw.sigunguCode.eq(cond.getSigunguCode()));
            }
        }

        if (hasText(cond.getDealYear())) {
            builder.and(qAptPriceGw.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qAptPriceGw.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceGw.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceGw.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceGw)
                .where(builder)
                .orderBy(qAptPriceGw.dealDate.desc())
                .limit(200)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findTradeList_CC(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qAptPriceCc.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qAptPriceCc.sigunguCode.eq(cond.getSigunguCode()));
            }
        }

        if (hasText(cond.getDealYear())) {
            builder.and(qAptPriceCc.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qAptPriceCc.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceCc.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceCc.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceCc)
                .where(builder)
                .orderBy(qAptPriceCc.dealDate.desc())
                .limit(200)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findTradeList_JL(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qAptPriceJl.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qAptPriceJl.sigunguCode.eq(cond.getSigunguCode()));
            }
        }

        if (hasText(cond.getDealYear())) {
            builder.and(qAptPriceJl.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qAptPriceJl.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceJl.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceJl.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceJl)
                .where(builder)
                .orderBy(qAptPriceJl.dealDate.desc())
                .limit(200)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findTradeList_JJ(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qAptPriceJj.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qAptPriceJj.sigunguCode.eq(cond.getSigunguCode()));
            }
        }

        if (hasText(cond.getDealYear())) {
            builder.and(qAptPriceJj.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qAptPriceJj.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceJj.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceJj.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceJj)
                .where(builder)
                .orderBy(qAptPriceJj.dealDate.desc())
                .limit(200)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 상승/하락 매매거래내역 거래
     * @param cond
     * @param type
     * @return
     */
    public List<AptPriceRaw> getTradeListComparePrev(TradeSearchCond cond, String type) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAptPriceRaw.sigunguCode.eq(cond.getSigunguCode()));
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
//        builder.and(qAptPriceRaw.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceRaw.dealYear.goe(Common.calcYearByTerm(1)));
        builder.and(qAptPriceRaw.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceRaw)
                .where(builder)
                .orderBy(qAptPriceRaw.dealDate.desc())
                .limit(200)
                .fetch();
    }

    public List<AptPriceGs> getTradeListComparePrev_GS(TradeSearchCond cond, String type) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAptPriceGs.sigunguCode.eq(cond.getSigunguCode()));
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceGs.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceGs.landDong.eq(cond.getLandDong()));
        }
        if (type.equals("0")) {
            builder.and(qAptPriceGs.prevDealAmt.lt(qAptPriceGs.dealAmt));
        } else {
            builder.and(qAptPriceGs.prevDealAmt.gt(qAptPriceGs.dealAmt));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        builder.and(qAptPriceGs.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceGs.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceGs)
                .where(builder)
                .orderBy(qAptPriceGs.dealDate.desc())
                .fetch();
    }

    public List<AptPriceGw> getTradeListComparePrev_GW(TradeSearchCond cond, String type) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAptPriceGw.sigunguCode.eq(cond.getSigunguCode()));
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceGw.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceGw.landDong.eq(cond.getLandDong()));
        }
        if (type.equals("0")) {
            builder.and(qAptPriceGw.prevDealAmt.lt(qAptPriceGw.dealAmt));
        } else {
            builder.and(qAptPriceGw.prevDealAmt.gt(qAptPriceGw.dealAmt));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        builder.and(qAptPriceGw.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceGw.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceGw)
                .where(builder)
                .orderBy(qAptPriceGw.dealDate.desc())
                .fetch();
    }

    public List<AptPriceCc> getTradeListComparePrev_CC(TradeSearchCond cond, String type) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAptPriceCc.sigunguCode.eq(cond.getSigunguCode()));
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceCc.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceCc.landDong.eq(cond.getLandDong()));
        }
        if (type.equals("0")) {
            builder.and(qAptPriceCc.prevDealAmt.lt(qAptPriceCc.dealAmt));
        } else {
            builder.and(qAptPriceCc.prevDealAmt.gt(qAptPriceCc.dealAmt));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        builder.and(qAptPriceCc.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceCc.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceCc)
                .where(builder)
                .orderBy(qAptPriceCc.dealDate.desc())
                .fetch();
    }

    public List<AptPriceJl> getTradeListComparePrev_JL(TradeSearchCond cond, String type) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAptPriceJl.sigunguCode.eq(cond.getSigunguCode()));
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceJl.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceJl.landDong.eq(cond.getLandDong()));
        }
        if (type.equals("0")) {
            builder.and(qAptPriceJl.prevDealAmt.lt(qAptPriceJl.dealAmt));
        } else {
            builder.and(qAptPriceJl.prevDealAmt.gt(qAptPriceJl.dealAmt));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        builder.and(qAptPriceJl.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceJl.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceJl)
                .where(builder)
                .orderBy(qAptPriceJl.dealDate.desc())
                .fetch();
    }
    public List<AptPriceJj> getTradeListComparePrev_JJ(TradeSearchCond cond, String type) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAptPriceJj.sigunguCode.eq(cond.getSigunguCode()));
        if (hasText(cond.getUaType())) {
            builder.and(qAptPriceJj.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceJj.landDong.eq(cond.getLandDong()));
        }
        if (type.equals("0")) {
            builder.and(qAptPriceJj.prevDealAmt.lt(qAptPriceJj.dealAmt));
        } else {
            builder.and(qAptPriceJj.prevDealAmt.gt(qAptPriceJj.dealAmt));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        builder.and(qAptPriceJj.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptPriceJj.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceJj)
                .where(builder)
                .orderBy(qAptPriceJj.dealDate.desc())
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
    public List<AptPriceResponseDto> findAptTradeList(TradeSearchCond cond, int term) {
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
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }
    public List<AptPriceResponseDto> findAptTradeList_GS(TradeSearchCond cond, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceGs.sigunguCode.eq(cond.getSigunguCode()));
        }
        builder.and(qAptPriceGs.dealYear.goe(Common.calcYearByTerm(term)));
        if (cond.getUseAreaTrunc() != 0) {
            builder.and(qAptPriceGs.useAreaTrunc.eq(cond.getUseAreaTrunc()));
        }
        if (hasText(cond.getAptName())) {
            builder.and(qAptPriceGs.aptName.eq(cond.getAptName()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceGs.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceGs)
                .where(builder)
                .orderBy(qAptPriceGs.dealDate.desc())
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findAptTradeList_GW(TradeSearchCond cond, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceGw.sigunguCode.eq(cond.getSigunguCode()));
        }
        builder.and(qAptPriceGw.dealYear.goe(Common.calcYearByTerm(term)));
        if (cond.getUseAreaTrunc() != 0) {
            builder.and(qAptPriceGw.useAreaTrunc.eq(cond.getUseAreaTrunc()));
        }
        if (hasText(cond.getAptName())) {
            builder.and(qAptPriceGw.aptName.eq(cond.getAptName()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceGw.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceGw)
                .where(builder)
                .orderBy(qAptPriceGw.dealDate.desc())
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findAptTradeList_CC(TradeSearchCond cond, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceCc.sigunguCode.eq(cond.getSigunguCode()));
        }
        builder.and(qAptPriceCc.dealYear.goe(Common.calcYearByTerm(term)));
        if (cond.getUseAreaTrunc() != 0) {
            builder.and(qAptPriceCc.useAreaTrunc.eq(cond.getUseAreaTrunc()));
        }
        if (hasText(cond.getAptName())) {
            builder.and(qAptPriceCc.aptName.eq(cond.getAptName()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceCc.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceCc)
                .where(builder)
                .orderBy(qAptPriceCc.dealDate.desc())
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findAptTradeList_JL(TradeSearchCond cond, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceJl.sigunguCode.eq(cond.getSigunguCode()));
        }
        builder.and(qAptPriceJl.dealYear.goe(Common.calcYearByTerm(term)));
        if (cond.getUseAreaTrunc() != 0) {
            builder.and(qAptPriceJl.useAreaTrunc.eq(cond.getUseAreaTrunc()));
        }
        if (hasText(cond.getAptName())) {
            builder.and(qAptPriceJl.aptName.eq(cond.getAptName()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceJl.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceJl)
                .where(builder)
                .orderBy(qAptPriceJl.dealDate.desc())
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> findAptTradeList_JJ(TradeSearchCond cond, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptPriceJj.sigunguCode.eq(cond.getSigunguCode()));
        }
        builder.and(qAptPriceJj.dealYear.goe(Common.calcYearByTerm(term)));
        if (cond.getUseAreaTrunc() != 0) {
            builder.and(qAptPriceJj.useAreaTrunc.eq(cond.getUseAreaTrunc()));
        }
        if (hasText(cond.getAptName())) {
            builder.and(qAptPriceJj.aptName.eq(cond.getAptName()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptPriceJj.landDong.eq(cond.getLandDong()));
        }

        return queryFactory.selectFrom(qAptPriceJj)
                .where(builder)
                .orderBy(qAptPriceJj.dealDate.desc())
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
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
        int tmp = Integer.parseInt(date.substring(0, 4))-1;
        String lastYear = Integer.toString(tmp);
        builder.and(qAptPriceRaw.dealYear.goe(lastYear));
        builder.and(qAptPriceRaw.newHighestPrice.eq(1));
        builder.and(qAptPriceRaw.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceRaw)
                .where(builder)
                .orderBy(qAptPriceRaw.dealDate.desc())
                .limit(200)
                .fetch();
    }

    public List<AptPriceGw> findNewHighestList_GW(String sigungucode, String uaType, String landDong) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigungucode)) {
            builder.and(qAptPriceGw.sigunguCode.eq(sigungucode));
        }
        if (hasText(uaType)) {
            if (!uaType.equals("UA01")) {
                builder.and(qAptPriceGw.useAreaType.eq(uaType));
            }
        }
        if (hasText(landDong)) {
            builder.and(qAptPriceGw.landDong.eq(landDong));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int tmp = Integer.parseInt(date.substring(0, 4))-1;
        String lastYear = Integer.toString(tmp);
        builder.and(qAptPriceRaw.dealYear.goe(lastYear));
        builder.and(qAptPriceGw.newHighestPrice.eq(1));
        builder.and(qAptPriceGw.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceGw)
                .where(builder)
                .orderBy(qAptPriceGw.dealDate.desc())
                .limit(200)
                .fetch();
    }

    public List<AptPriceGs> findNewHighestList_GS(String sigungucode, String uaType, String landDong) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigungucode)) {
            builder.and(qAptPriceGs.sigunguCode.eq(sigungucode));
        }
        if (hasText(uaType)) {
            if (!uaType.equals("UA01")) {
                builder.and(qAptPriceGs.useAreaType.eq(uaType));
            }
        }
        if (hasText(landDong)) {
            builder.and(qAptPriceGs.landDong.eq(landDong));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int tmp = Integer.parseInt(date.substring(0, 4))-1;
        String lastYear = Integer.toString(tmp);
        builder.and(qAptPriceRaw.dealYear.goe(lastYear));
        builder.and(qAptPriceGs.newHighestPrice.eq(1));
        builder.and(qAptPriceGs.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceGs)
                .where(builder)
                .orderBy(qAptPriceGs.dealDate.desc())
                .limit(200)
                .fetch();
    }

    public List<AptPriceCc> findNewHighestList_CC(String sigungucode, String uaType, String landDong) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigungucode)) {
            builder.and(qAptPriceCc.sigunguCode.eq(sigungucode));
        }
        if (hasText(uaType)) {
            if (!uaType.equals("UA01")) {
                builder.and(qAptPriceCc.useAreaType.eq(uaType));
            }
        }
        if (hasText(landDong)) {
            builder.and(qAptPriceCc.landDong.eq(landDong));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int tmp = Integer.parseInt(date.substring(0, 4))-1;
        String lastYear = Integer.toString(tmp);
        builder.and(qAptPriceRaw.dealYear.goe(lastYear));
        builder.and(qAptPriceCc.newHighestPrice.eq(1));
        builder.and(qAptPriceCc.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceCc)
                .where(builder)
                .orderBy(qAptPriceCc.dealDate.desc())
                .limit(200)
                .fetch();
    }

    public List<AptPriceJl> findNewHighestList_JL(String sigungucode, String uaType, String landDong) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigungucode)) {
            builder.and(qAptPriceJl.sigunguCode.eq(sigungucode));
        }
        if (hasText(uaType)) {
            if (!uaType.equals("UA01")) {
                builder.and(qAptPriceJl.useAreaType.eq(uaType));
            }
        }
        if (hasText(landDong)) {
            builder.and(qAptPriceJl.landDong.eq(landDong));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int tmp = Integer.parseInt(date.substring(0, 4))-1;
        String lastYear = Integer.toString(tmp);
        builder.and(qAptPriceRaw.dealYear.goe(lastYear));
        builder.and(qAptPriceJl.newHighestPrice.eq(1));
        builder.and(qAptPriceJl.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceJl)
                .where(builder)
                .orderBy(qAptPriceJl.dealDate.desc())
                .limit(200)
                .fetch();
    }

    public List<AptPriceJj> findNewHighestList_JJ(String sigungucode, String uaType, String landDong) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigungucode)) {
            builder.and(qAptPriceJj.sigunguCode.eq(sigungucode));
        }
        if (hasText(uaType)) {
            if (!uaType.equals("UA01")) {
                builder.and(qAptPriceJj.useAreaType.eq(uaType));
            }
        }
        if (hasText(landDong)) {
            builder.and(qAptPriceJj.landDong.eq(landDong));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int tmp = Integer.parseInt(date.substring(0, 4))-1;
        String lastYear = Integer.toString(tmp);
        builder.and(qAptPriceRaw.dealYear.goe(lastYear));
        builder.and(qAptPriceJj.newHighestPrice.eq(1));
        builder.and(qAptPriceJj.cnclDealDate.eq(""));

        return queryFactory.selectFrom(qAptPriceJj)
                .where(builder)
                .orderBy(qAptPriceJj.dealDate.desc())
                .limit(200)
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
                .limit(500)
                .fetch();
    }

    public List<AptDistributionRaw> getTradeDistribution_ByName(String dealYear, String sigunguCode, String landDong, String aptName) {
        BooleanBuilder builder = new BooleanBuilder();
//        if (hasText(dealYear)) {
//            builder.and(qAptDistributionRaw.dealYear.eq(dealYear));
//        }
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
                .orderBy(qAptDistributionRaw.dealDate.desc())
                .limit(500)
                .fetch();
    }
}

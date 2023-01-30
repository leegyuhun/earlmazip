package com.earlmazip.repository;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.AptPriceResponseDto;
import com.earlmazip.controller.dto.StatResponseDto;
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
public class ApiRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QAptPriceRaw qAptPriceRaw = QAptPriceRaw.aptPriceRaw;
    QAptPriceGs qAptPriceGs = QAptPriceGs.aptPriceGs;
    QAptPriceGw qAptPriceGw = QAptPriceGw.aptPriceGw;
    QAptPriceCc qAptPriceCc = QAptPriceCc.aptPriceCc;
    QAptPriceJl qAptPriceJl = QAptPriceJl.aptPriceJl;
    QAptPriceJj qAptPriceJj = QAptPriceJj.aptPriceJj;

    QAptLeaseRaw qAptLeaseRaw = QAptLeaseRaw.aptLeaseRaw;

    QStatSigunguYYMM qStatSigunguYYMM = QStatSigunguYYMM.statSigunguYYMM;

    public ApiRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 최근200 매매내역 조회
     * @param cond
     * @return
     */
    public List<AptPriceResponseDto> getTradeListMonthlyV1(TradeSearchCond cond) {
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
                .limit(500)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeListMonthlyV1_GS(TradeSearchCond cond) {
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
                .limit(500)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeListMonthlyV1_GW(TradeSearchCond cond) {
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
                .limit(500)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeListMonthlyV1_CC(TradeSearchCond cond) {
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
                .limit(500)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeListMonthlyV1_JL(TradeSearchCond cond) {
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
                .limit(500)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptPriceResponseDto> getTradeListMonthlyV1_JJ(TradeSearchCond cond) {
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
                .limit(500)
                .fetch().stream().map(AptPriceResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseListMonthlyV1(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptLeaseRaw.sigunguCode.eq(cond.getSigunguCode()));
        }
        if (hasText(cond.getDealYear())) {
            builder.and(qAptLeaseRaw.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qAptLeaseRaw.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qAptLeaseRaw.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptLeaseRaw.landDong.eq(cond.getLandDong()));
        }
        if (hasText(cond.getLeaseType())) {
            if (cond.getLeaseType().equals("0")) {
                builder.and(qAptLeaseRaw.monthlyRent.eq(0)); //전세 (= 0)
            } else {
                builder.and(qAptLeaseRaw.monthlyRent.gt(0)); //월세 (> 0)
            }
        }
        return queryFactory.selectFrom(qAptLeaseRaw)
                .where(builder)
                .orderBy(qAptLeaseRaw.dealDate.desc())
                .limit(500)
                .fetch().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeListMonthlyV1(String sigunguCode, String year, String uaType) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qStatSigunguYYMM.sigunguCode.eq(sigunguCode));
        }

        if (hasText(year)) {
            builder.and(qStatSigunguYYMM.dealYear.eq(year));
        }

        if (hasText(uaType)) {
            builder.and(qStatSigunguYYMM.useAreaType.eq(uaType));
        }

        return queryFactory.selectFrom(qStatSigunguYYMM)
                .where(builder)
                .orderBy(qStatSigunguYYMM.dealYYMM.desc())
                .fetch().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }
}

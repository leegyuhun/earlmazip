package com.earlmazip.repository;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.domain.AptLeaseRaw;
import com.earlmazip.domain.OfficeLease;
import com.earlmazip.domain.QAptLeaseRaw;
import com.earlmazip.domain.QOfficeLease;
import com.earlmazip.utils.Common;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class LeaseRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public LeaseRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QAptLeaseRaw qAptLeaseRaw = QAptLeaseRaw.aptLeaseRaw;
    QOfficeLease qOfficeLease = QOfficeLease.officeLease;

    public List<AptLeaseRaw> findLeaseList(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qAptLeaseRaw.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qAptLeaseRaw.sigunguCode.eq(cond.getSigunguCode()));
            }
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
                .limit(200)
                .fetch();
    }

    public List<OfficeLease> findLeaseList_Office(TradeSearchCond cond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            if (cond.getSigunguCode().length() == 2) {
                builder.and(qOfficeLease.areaCode.eq(cond.getSigunguCode()));
            } else {
                builder.and(qOfficeLease.sigunguCode.eq(cond.getSigunguCode()));
            }
        }
        if (hasText(cond.getDealYear())) {
            builder.and(qOfficeLease.dealYear.eq(cond.getDealYear()));
            if (hasText(cond.getDealMon())) {
                builder.and(qOfficeLease.dealMon.eq(cond.getDealMon()));
            }
        }
        if (hasText(cond.getUaType())) {
            builder.and(qOfficeLease.useAreaType.eq(cond.getUaType()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qOfficeLease.landDong.eq(cond.getLandDong()));
        }
        if (hasText(cond.getLeaseType())) {
            if (cond.getLeaseType().equals("0")) {
                builder.and(qOfficeLease.monthlyRent.eq(0)); //전세 (= 0)
            } else {
                builder.and(qOfficeLease.monthlyRent.gt(0)); //월세 (> 0)
            }
        }
        return queryFactory.selectFrom(qOfficeLease)
                .where(builder)
                .orderBy(qOfficeLease.dealDate.desc())
                .limit(200)
                .fetch();
    }

    /**
     * 서울 전/월세 갱신현황
     * @param sigungucode
     * @return
     */
    public List<AptLeaseRaw> findLeaseRenewalList(String sigungucode, String landDong, String uaType) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAptLeaseRaw.sigunguCode.eq(sigungucode));
        if (!uaType.equals("UA01")) {
            builder.and(qAptLeaseRaw.useAreaType.eq(uaType));
        }
        if (hasText(landDong)) {
            builder.and(qAptLeaseRaw.landDong.eq(landDong));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
//        String date = simpleDateFormat.format(new Date());
//        builder.and(qAptLeaseRaw.dealYear.eq(date.substring(0, 4)));
        builder.and(qAptLeaseRaw.dealType.eq("갱신"));

        return queryFactory.selectFrom(qAptLeaseRaw)
                .where(builder)
                .orderBy(qAptLeaseRaw.dealDate.desc())
                .limit(200)
                .fetch();
    }

    public List<AptLeaseRaw> findAptLeaseList(TradeSearchCond cond, String type, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(cond.getSigunguCode())) {
            builder.and(qAptLeaseRaw.sigunguCode.eq(cond.getSigunguCode()));
        }
        if (hasText(cond.getAptName())) {
            builder.and(qAptLeaseRaw.aptName.eq(cond.getAptName()));
        }
        if (cond.getUseAreaTrunc() > 0) {
            builder.and(qAptLeaseRaw.useAreaTrunc.eq(cond.getUseAreaTrunc()));
        }
        if (hasText(cond.getLandDong())) {
            builder.and(qAptLeaseRaw.landDong.eq(cond.getLandDong()));
        }
        if (type.equals("0")) {
            builder.and(qAptLeaseRaw.monthlyRent.eq(0)); //전세 (= 0)
        } else {
            builder.and(qAptLeaseRaw.monthlyRent.gt(0)); //월세 (> 0)
        }
        builder.and(qAptLeaseRaw.dealYear.goe(Common.calcYearByTerm(term)));
        return queryFactory.selectFrom(qAptLeaseRaw)
                .where(builder)
                .orderBy(qAptLeaseRaw.dealDate.desc())
                .fetch();
    }
}
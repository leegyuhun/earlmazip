package com.earlmazip.repository;

import com.earlmazip.controller.dto.RankLeaseResponseDto;
import com.earlmazip.controller.dto.StatLeaseResponseDto;
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
public class StatLeaseRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QStatSigunguLease qStatSigunguLease = QStatSigunguLease.statSigunguLease;
    QRankLease qRankLease = QRankLease.rankLease;

    public StatLeaseRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StatSigunguLease> getStatLeaseList(String sigunguCode, String uaType, String term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qStatSigunguLease.sigunguCode.eq(sigunguCode));
        }
        if (hasText(uaType)) {
            builder.and(qStatSigunguLease.useAreaType.eq(uaType));
        }
        if (hasText(term)) {
            builder.and(qStatSigunguLease.dealYear.goe(Common.calcYearByTerm(term)));
        }

        builder.and(qStatSigunguLease.leaseType.eq("전세"));

        return queryFactory.selectFrom(qStatSigunguLease)
                .where(builder)
                .orderBy(qStatSigunguLease.dealYYMM.desc())
                .fetch();
    }

    public List<RankLease> getTopLeaseSigungu(String sigunguCode, String uaType, int leaseType) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qRankLease.sigunguCode.eq(sigunguCode));
        }
        if (hasText(uaType)) {
            builder.and(qRankLease.useAreaType.eq(uaType));
        }
        if (leaseType == 0) {
            builder.and(qRankLease.leaseType.eq("전세"));
        } else {
            builder.and(qRankLease.leaseType.eq("월세"));
        }
        if (leaseType == 0) {
            return queryFactory.selectFrom(qRankLease)
                    .where(builder)
                    .orderBy(qRankLease.deposit.desc())
                    .fetch();
        } else {
            return queryFactory.selectFrom(qRankLease)
                    .where(builder)
                    .orderBy(qRankLease.monthlyRent.desc())
                    .fetch();
        }
    }
}

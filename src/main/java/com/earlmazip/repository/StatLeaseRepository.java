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

    public StatLeaseRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StatLeaseResponseDto> getStatLeaseList_Gyunggi(String sidoCode, int term) {
        return em.createQuery("select a from StatSidoLease a"
                + " where a.sidoCode = :sidoCode and a.leaseType = '전세' "
                + "   and a.dealYear >= :searchYear "
                + " order by a.dealYYMM desc", StatSidoLease.class)
                .setParameter("sidoCode", sidoCode)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
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

    public List<StatLeaseResponseDto> statLeaseMonthlySido(String sidoCode) {
        return em.createQuery("select a from StatSidoLease a"
                        + " where a.sidoCode = :sidoCode and a.leaseType = '월세' "
                        + " order by a.dealYYMM desc", StatSidoLease.class)
                .setParameter("sidoCode", sidoCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatLeaseResponseDto> getStatLeaseList_Seoul(String sigunguCode, int term) {
        if (sigunguCode.length() == 2) {
            return em.createQuery("select a from StatSidoLease a"
                            + " where a.sidoCode = :sigunguCode and a.leaseType = '전세' "
                            + "   and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSidoLease.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery("select a from StatSigunguLease a"
                            + " where a.sigunguCode = :sigunguCode and a.leaseType = '전세' "
                            + "   and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSigunguLease.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<StatLeaseResponseDto> getStatLeaseList_Seoul84(String sigunguCode, int gubn, int term) {
        if (gubn == 0) {
            return em.createQuery("select a from StatSigunguLease84 a"
                            + " where a.sigunguCode = :sigunguCode and a.leaseType = '전세' "
                            + "   and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSigunguLease84.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery("select a from StatSigunguLease84 a"
                            + " where a.sigunguCode = :sigunguCode and a.leaseType = '월세' "
                            + "   and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSigunguLease84.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<StatLeaseResponseDto> getStatLeaseMonthlySigungu(String sigunguCode) {
        return em.createQuery("select a from StatSigunguLease a"
                        + " where a.sigunguCode = :sigunguCode and a.leaseType = '월세' "
                        + " order by a.dealYYMM desc", StatSigunguLease.class)
                .setParameter("sigunguCode", sigunguCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<RankLeaseResponseDto> getTopLeaseSigungu(String sigunguCode, String uaType, int leaseType) {
        if (leaseType == 0) {
            return em.createQuery("select a from RankLease a"
                            + " where a.sigunguCode = :sigunguCode and a.leaseType = '전세' "
                            + "   and a.useAreaType = :uaType "
                            + " order by a.deposit desc", RankLease.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("uaType", uaType)
                    .getResultList().stream().map(RankLeaseResponseDto::new).collect(Collectors.toList());

        } else {
            return em.createQuery("select a from RankLease a"
                            + " where a.sigunguCode = :sigunguCode and a.leaseType = '월세' "
                            + "   and a.useAreaType = :uaType "
                            + " order by a.monthlyRent desc", RankLease.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("uaType", uaType)
                    .getResultList().stream().map(RankLeaseResponseDto::new).collect(Collectors.toList());
        }
    }
}

package com.earlmazip.repository;

import com.earlmazip.controller.dto.AptLeaseResponseDto;
import com.earlmazip.controller.dto.TradeSearchCond;
import com.earlmazip.domain.AptLeaseRaw;
import com.earlmazip.domain.QAptLeaseRaw;
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
public class LeaseRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public LeaseRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QAptLeaseRaw qAptLeaseRaw = QAptLeaseRaw.aptLeaseRaw;

    public List<AptLeaseRaw> findLeaseList(TradeSearchCond cond) {
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
                .limit(200)
                .fetch();
    }

    /* 서울 갱신현황 */
    public List<AptLeaseResponseDto> getLeaseRenewalList_SeoulSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '11' "
                        + "   and a.sigunguCode = :sigunguCode "
                        + "   and a.befMonthlyRent = 0 "
                        + "   and a.dealType = '갱신' "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseRenewalList_GyunggiSigungu(String sigungucode) {
        if (sigungucode.length() == 4) {
            return em.createQuery("select a from AptLeaseRaw a"
                            + " where a.dealYear = 2022 and a.areaCode = '41' "
                            + "   and a.sidoCode = :sigunguCode "
                            + "   and a.befMonthlyRent = 0 "
                            + "   and a.dealType = '갱신' "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("sidoCode", sigungucode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());

        } else {
            return em.createQuery("select a from AptLeaseRaw a"
                            + " where a.dealYear = 2022 and a.areaCode = '41' "
                            + "   and a.sigunguCode = :sigunguCode "
                            + "   and a.befMonthlyRent = 0 "
                            + "   and a.dealType = '갱신' "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("sigunguCode", sigungucode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        }

    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_IncheonSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '28' "
                        + "   and a.sigunguCode = :sigunguCode "
                        + "   and a.monthlyRent > 0 "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
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

    public List<AptLeaseResponseDto> getLeaseList_ByName(String regnCode, String dong, String aptName, int ua, int term) {
        if (ua == 0) {
            return em.createQuery(" select a from AptLeaseRaw a "
                            + " where a.sigunguCode = :regncode "
                            + "   and a.landDong = :landDong"
                            + "   and a.dealYear >= :searchYear "
                            + "   and a.aptName = :aptname "
                            + "   and a.monthlyRent = 0 "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("regncode", regnCode)
                    .setParameter("landDong", dong)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .setParameter("aptname", aptName)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery(" select a from AptLeaseRaw a "
                            + " where a.sigunguCode = :regncode "
                            + "   and a.landDong = :landDong"
                            + "   and a.dealYear >= :searchYear "
                            + "   and a.aptName = :aptname "
                            + "   and a.monthlyRent = 0 "
                            + "   and a.useAreaTrunc = :ua "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("regncode", regnCode)
                    .setParameter("landDong", dong)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .setParameter("aptname", aptName)
                    .setParameter("ua", ua)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<AptLeaseResponseDto> getMonthlyList_ByName(String regnCode, String dong, String aptName, int ua, int term) {
        if (ua == 0) {
            return em.createQuery(" select a from AptLeaseRaw a "
                            + " where a.sigunguCode = :regncode "
                            + "   and a.landDong = :landDong"
                            + "   and a.dealYear >= :searchYear "
                            + "   and a.aptName = :aptname "
                            + "   and a.monthlyRent > 0 "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("regncode", regnCode)
                    .setParameter("landDong", dong)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .setParameter("aptname", aptName)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery(" select a from AptLeaseRaw a "
                            + " where a.sigunguCode = :regncode "
                            + "   and a.landDong = :landDong"
                            + "   and a.dealYear >= :searchYear "
                            + "   and a.aptName = :aptname "
                            + "   and a.monthlyRent > 0 "
                            + "   and a.useAreaTrunc = :ua "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("regncode", regnCode)
                    .setParameter("landDong", dong)
                    .setParameter("searchYear", Common.calcYearByTerm(term))
                    .setParameter("aptname", aptName)
                    .setParameter("ua", ua)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        }
    }
}
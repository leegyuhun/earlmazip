package com.earlmazip.repository;

import com.earlmazip.controller.dto.StatLeaseAnalysisDto;
import com.earlmazip.domain.QStatLeaseAnalysis;
import com.earlmazip.domain.StatLeaseAnalysis;
import com.earlmazip.utils.Common;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class LeaseAnalysisRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QStatLeaseAnalysis qStatLeaseAnalysis = QStatLeaseAnalysis.statLeaseAnalysis;

    public LeaseAnalysisRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StatLeaseAnalysis> getLeaseAnalysisList(String sigunguCode, int term) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qStatLeaseAnalysis.sigunguCode.eq(sigunguCode));
        }
        builder.and(qStatLeaseAnalysis.dealYear.goe(Common.calcYearByTerm(term)));
        return queryFactory.selectFrom(qStatLeaseAnalysis)
                .where(builder)
                .orderBy(qStatLeaseAnalysis.dealYYMM.desc())
                .fetch();
    }
}

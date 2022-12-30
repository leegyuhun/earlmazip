package com.earlmazip.repository;

import com.earlmazip.controller.dto.EcosDataResponseDto;
import com.earlmazip.domain.EcosData;
import com.earlmazip.domain.QEcosData;
import com.earlmazip.utils.Common;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EcosDataRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public EcosDataRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    QEcosData qEcosData = QEcosData.ecosData;

    public List<EcosData> getEcosData(String statCode, String itemCode1, String itemCode2, String term) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qEcosData.statCode.eq(statCode));
        builder.and(qEcosData.itemCode1.eq(itemCode1));
        builder.and(qEcosData.itemCode2.eq(itemCode2));
        builder.and(qEcosData.year.goe(Common.calcYearByTerm(term)));
        return queryFactory.selectFrom(qEcosData)
                .where(builder)
                .orderBy(qEcosData.date.asc())
                .fetch();
    }
}

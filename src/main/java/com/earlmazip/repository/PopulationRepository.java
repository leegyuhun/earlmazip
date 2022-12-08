package com.earlmazip.repository;

import com.earlmazip.domain.Population;
import com.earlmazip.domain.QPopulation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class PopulationRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QPopulation qPopulation = QPopulation.population;

    public PopulationRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Population> findPopulationSigungu(String sigunguCode, int sortType){
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(sigunguCode)) {
            builder.and(qPopulation.sigunguCode.like(sigunguCode+"%"));
        }

        if (sortType == 1) {
            return queryFactory.selectFrom(qPopulation)
                    .where(builder)
                    .orderBy(qPopulation.koreanCount.desc())
                    .fetch();
        } else if (sortType == 2) {
            return queryFactory.selectFrom(qPopulation)
                    .where(builder)
                    .orderBy(qPopulation.foreignerCount.desc())
                    .fetch();
        } else if (sortType == 3) {
            return queryFactory.selectFrom(qPopulation)
                    .where(builder)
                    .orderBy(qPopulation.oldCount.desc())
                    .fetch();
        }else{
            return queryFactory.selectFrom(qPopulation)
                    .where(builder)
                    .orderBy(qPopulation.sigunguCode.asc())
                    .fetch();
        }
    }
}

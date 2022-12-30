package com.earlmazip.repository;

import com.earlmazip.domain.CodeInfo;
import com.earlmazip.domain.QCodeInfo;
import com.earlmazip.domain.QSigunguCode;
import com.earlmazip.domain.SigunguCode;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Repository
public class CodeInfoRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    QCodeInfo qCodeInfo = QCodeInfo.codeInfo;

    QSigunguCode qSigunguCode = QSigunguCode.sigunguCode1;

    public CodeInfoRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public String getCodeName(String code) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(code)) {
            builder.and(qCodeInfo.codeNmbr.eq(code));
        }

        CodeInfo codeInfo = queryFactory.selectFrom(qCodeInfo)
                .where(builder)
                .fetchOne();

        return codeInfo.getCodeName();
    }

    public List<SigunguCode> getSigunguList(String areaCode) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qSigunguCode.areaCode.eq(areaCode));

        return queryFactory.selectFrom(qSigunguCode)
                .where(builder)
                .fetch();
    }
}

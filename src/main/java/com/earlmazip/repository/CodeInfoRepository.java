package com.earlmazip.repository;

import com.earlmazip.domain.CodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CodeInfoRepository {

    private final EntityManager em;

    public String getCodeName(String code) {
        List<CodeInfo> codeInfos = em.createQuery("select a from CodeInfo a where a.codeNmbr = :code", CodeInfo.class)
                .setParameter("code", code)
                .getResultList();
        if (codeInfos.size() == 0){
            return "-";
        }
        else {
            CodeInfo codeInfo = codeInfos.get(0);
            return codeInfo.getCodeName();
        }
    }
}

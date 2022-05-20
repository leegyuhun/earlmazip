package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.domain.EcosData;
import aptdata.earlmazip.domain.SiteInfo;
import aptdata.earlmazip.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class EcosDataRepository {

    private final EntityManager em;

    public List<EcosDataResponseDto> getEcosData(String statCode, String itemCode1, String itemCode2, String term) {
        return em.createQuery(" select a from EcosData a "
                        + " where a.statCode = :statCode "
                        + "   and itemCode1 = :itemCode1 "
                        + "   and itemCode2 = :itemCode2 "
                        + "   and a.year >= :searchYear "
                        + " order by a.date asc ", EcosData.class)
                .setParameter("statCode", statCode)
                .setParameter("itemCode1", itemCode1)
                .setParameter("itemCode2", itemCode2)
                .setParameter("searchYear", Common.calcYearByTerm(term))
                .getResultList().stream().map(EcosDataResponseDto::new).collect(Collectors.toList());
    }
}

package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseAnalysisDto;
import aptdata.earlmazip.domain.AptLeaseRaw;
import aptdata.earlmazip.domain.StatLeaseAnalysis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LeaseAnalysisRepository {

    private final EntityManager em;

    public List<StatLeaseAnalysisDto> getLeaseAnalysisList(String gubnCode) {
        return em.createQuery("select a from StatLeaseAnalysis a"
                        + " where a.gubnCode = :gubnCode "
                        + " order by a.dealYYMM desc", StatLeaseAnalysis.class)
                .setParameter("gubnCode", gubnCode)
                .getResultList().stream().map(StatLeaseAnalysisDto::new).collect(Collectors.toList());
    }
}

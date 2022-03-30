package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.domain.AptLeaseRaw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LeaseRepository {

    private final EntityManager em;

    public List<AptLeaseResponseDto> getLeaseList_SeoulSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear >= 2021 and a.areaCode = '11' and a.sigunguCode = :sigunguCode"
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseList_GyunggiSido(String sidocode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear >= 2021 and a.areaCode = '41' and a.sidoCode = :sidoCode"
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sidoCode", sidocode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseList_IncheonSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear >= 2021 and a.areaCode = '28' and a.sigunguCode = :sigunguCode"
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }
}

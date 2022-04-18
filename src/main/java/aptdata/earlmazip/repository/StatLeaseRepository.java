package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.RankYearResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StatLeaseRepository {
    private final EntityManager em;

    public List<StatLeaseResponseDto> statLeaseSido(String sidoCode) {
        return em.createQuery("select a from StatSidoLease a"
        + " where a.sidoCode = :sidoCode and a.leaseType = '전세' "
        + " order by a.dealYYMM desc", StatSidoLease.class)
                .setParameter("sidoCode", sidoCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatLeaseResponseDto> statLeaseMonthlySido(String sidoCode) {
        return em.createQuery("select a from StatSidoLease a"
                        + " where a.sidoCode = :sidoCode and a.leaseType = '월세' "
                        + " order by a.dealYYMM desc", StatSidoLease.class)
                .setParameter("sidoCode", sidoCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatLeaseResponseDto> getStatLeaseSigungu(String sigunguCode) {
        return em.createQuery("select a from StatSigunguLease a"
                        + " where a.sigunguCode = :sigunguCode and a.leaseType = '전세' "
                        + " order by a.dealYYMM desc", StatSigunguLease.class)
                .setParameter("sigunguCode", sigunguCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatLeaseResponseDto> getStatLeaseMonthlySigungu(String sigunguCode) {
        return em.createQuery("select a from StatSigunguLease a"
                        + " where a.sigunguCode = :sigunguCode and a.leaseType = '월세' "
                        + " order by a.dealYYMM desc", StatSigunguLease.class)
                .setParameter("sigunguCode", sigunguCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }
}

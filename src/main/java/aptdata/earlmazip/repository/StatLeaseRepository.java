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

    public List<StatLeaseResponseDto> getStatLeaseList_Gyunggi(String sidoCode, int term) {
        return em.createQuery("select a from StatSidoLease a"
                + " where a.sidoCode = :sidoCode and a.leaseType = '전세' "
                + "   and a.dealYear >= :searchYear "
                + " order by a.dealYYMM desc", StatSidoLease.class)
                .setParameter("sidoCode", sidoCode)
                .setParameter("searchYear", calcYearByTerm(term))
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatLeaseResponseDto> statLeaseMonthlySido(String sidoCode) {
        return em.createQuery("select a from StatSidoLease a"
                        + " where a.sidoCode = :sidoCode and a.leaseType = '월세' "
                        + " order by a.dealYYMM desc", StatSidoLease.class)
                .setParameter("sidoCode", sidoCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatLeaseResponseDto> getStatLeaseList_Seoul(String sigunguCode, int term) {
        if (sigunguCode.length() == 2) {
            return em.createQuery("select a from StatSidoLease a"
                            + " where a.sidoCode = :sigunguCode and a.leaseType = '전세' "
                            + "   and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSidoLease.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery("select a from StatSigunguLease a"
                            + " where a.sigunguCode = :sigunguCode and a.leaseType = '전세' "
                            + "   and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSigunguLease.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<StatLeaseResponseDto> getStatLeaseList_Seoul84(String sigunguCode, int term) {
            return em.createQuery("select a from StatSigunguLease84 a"
                            + " where a.sigunguCode = :sigunguCode and a.leaseType = '전세' "
                            + "   and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSigunguLease84.class)
                    .setParameter("sigunguCode", sigunguCode)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<StatLeaseResponseDto> getStatLeaseMonthlySigungu(String sigunguCode) {
        return em.createQuery("select a from StatSigunguLease a"
                        + " where a.sigunguCode = :sigunguCode and a.leaseType = '월세' "
                        + " order by a.dealYYMM desc", StatSigunguLease.class)
                .setParameter("sigunguCode", sigunguCode)
                .getResultList().stream().map(StatLeaseResponseDto::new).collect(Collectors.toList());
    }

    private String calcYearByTerm(int term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int nowInt = Integer.parseInt(date.substring(0,4));
        // 현재날짜-term = 조회 기준일자
        return Integer.toString(nowInt - term);
    }
}

package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.RankYearResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.domain.RankYear;
import aptdata.earlmazip.domain.StatAreaYYMM;
import aptdata.earlmazip.domain.StatSidoLease;
import aptdata.earlmazip.domain.StatSidoYYMM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.chrono.HijrahDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StatRepository {
    private final EntityManager em;

    public List<StatResponseDto> findSeoul(String term){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());
        int termInt = Integer.parseInt(term);
        int nowInt = Integer.parseInt(date.substring(0,4));
        String searchYear = Integer.toString(nowInt - termInt);
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("searchYear", searchYear)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> findSeoulYear(String year){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + "   and a.dealYear = :dealYaer"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("dealYaer", year)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> findSeoulUA(String ua){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '11' and a.useAreaType = :useAreaType"
                        + " and a.dealYear > 2019 "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("useAreaType", ua)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<RankYearResponseDto> findSeoulTop(String year, String sigungucode){
        return em.createQuery(" select a from RankYear a "
                            + " where a.gubnCode = :sigunguCode and a.dealYear = :dealYear"
                            + " order by a.dealAmt desc", RankYear.class)
                .setParameter("sigunguCode", sigungucode)
                .setParameter("dealYear", year)
                .setMaxResults(100)
                .getResultList().stream().map(RankYearResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> findGyungGi(String term){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(new Date());
        int termInt = Integer.parseInt(term);
        int nowInt = Integer.parseInt(date.substring(0,4));
        String searchYear = Integer.toString(nowInt - termInt);

        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and useAreaType = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("searchYear", searchYear)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> findGyungGiYear(String year){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and useAreaType = 'UA01'"
                        + "    and a.dealYear = :dealYear"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("dealYear", year)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> findGyungGiUA(String ua){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and a.useAreaType = :useAreaType"
                        + " and a.dealYear > 2019 "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("useAreaType", ua)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> findGyungGiSi(String sidoCode){
        return em.createQuery("select a from StatSidoYYMM a"
                        + " where a.sidoCode = :sidoCode and use_area_type = 'UA01'"
                        + " and a.dealYear > 2018 "
                        + " order by a.dealYYMM desc", StatSidoYYMM.class)
                .setParameter("sidoCode", sidoCode)
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<RankYearResponseDto> findGyungGiTop(String year, String sidocode){
        return em.createQuery(" select a from RankYear a "
                        + " where a.gubnCode = :sidoCode and a.dealYear = :dealYear "
                        + " order by a.dealAmt desc", RankYear.class)
                .setParameter("sidoCode", sidocode)
                .setParameter("dealYear", year)
                .setMaxResults(100)
                .getResultList().stream().map(RankYearResponseDto::new).collect(Collectors.toList());
    }

    public List<RankYearResponseDto> findIncheonTop(String year, String sigungucode){
        return em.createQuery(" select a from RankYear a "
                        + " where a.gubnCode = :sigungucode and a.dealYear = :dealYear "
                        + " order by a.dealAmt desc", RankYear.class)
                .setParameter("sigungucode", sigungucode)
                .setParameter("dealYear", year)
                .setMaxResults(100)
                .getResultList().stream().map(RankYearResponseDto::new).collect(Collectors.toList());
    }

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

    public List<StatResponseDto> getStatNewHighestAndTradeCount(String sidoCode) {
        if (sidoCode.length() == 4) {
            return em.createQuery("select a from StatSidoYYMM a "
                            + " where a.sidoCode = :sidoCode and use_area_type = 'UA01' "
                            + " and deal_year > 2016 "
                            + " order by a.dealYYMM desc ", StatSidoYYMM.class)
                    .setParameter("sidoCode", sidoCode)
                    .getResultList().stream().map(StatResponseDto::new)
                    .collect(Collectors.toList());
        } else {
            return em.createQuery("select a from StatAreaYYMM a "
                            + " where a.areaCode = :sidoCode and use_area_type = 'UA01' "
                            + " and deal_year > 2016 "
                            + " order by a.dealYYMM desc ", StatAreaYYMM.class)
                    .setParameter("sidoCode", sidoCode)
                    .getResultList().stream().map(StatResponseDto::new)
                    .collect(Collectors.toList());
        }

    }

}

package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.RankYearResponseDto;
import aptdata.earlmazip.controller.dto.StatLeaseResponseDto;
import aptdata.earlmazip.controller.dto.StatResponseDto;
import aptdata.earlmazip.domain.*;
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

    public List<StatResponseDto> getStatTradeList(String areaCode, String term){        
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = :areaCode and use_area_type = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("areaCode", areaCode)
                .setParameter("searchYear", calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeList_BySigungu(String sigunguCode, String term){
        return em.createQuery("select a from StatSigunguYYMM a"
                        + " where a.sigunguCode = :sigunguCode and use_area_type = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatSigunguYYMM.class)
                .setParameter("searchYear", calcYearByTerm(term))
                .setParameter("sigunguCode", sigunguCode)
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

    /**
     * 평형별 매매가 통계 조회
     * @param regnCode: 지역코드
     * @param ua: UA01(전체), UA02(~59), UA03(59-85), UA04(85-102), UA05(102-135), UA06(135~)
     * @param term
     * @return
     */
    public List<StatResponseDto> getStatTradeByUseAreaList(String regnCode, String ua, String term){
        if (regnCode.length() == 2) {
            return em.createQuery("select a from StatAreaYYMM a"
                            + " where a.areaCode = :regnCode and a.useAreaType = :useAreaType"
                            + " and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatAreaYYMM.class)
                    .setParameter("regnCode", regnCode)
                    .setParameter("useAreaType", ua)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
        } else if (regnCode.length() == 4) {
            return em.createQuery("select a from StatSidoYYMM a"
                            + " where a.sidoCode = :regnCode and a.useAreaType = :useAreaType"
                            + " and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSidoYYMM.class)
                    .setParameter("regnCode", regnCode)
                    .setParameter("useAreaType", ua)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery("select a from StatSigunguYYMM a"
                            + " where a.sigunguCode = :regnCode and a.useAreaType = :useAreaType"
                            + " and a.dealYear >= :searchYear "
                            + " order by a.dealYYMM desc", StatSigunguYYMM.class)
                    .setParameter("regnCode", regnCode)
                    .setParameter("useAreaType", ua)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
        }
    }

    public List<RankYearResponseDto> getStatTradeTopSeoulByYear(String year, String sigungucode){
        return em.createQuery(" select a from RankYear a "
                            + " where a.gubnCode = :sigunguCode and a.dealYear = :dealYear"
                            + " order by a.dealAmt desc", RankYear.class)
                .setParameter("sigunguCode", sigungucode)
                .setParameter("dealYear", year)
                .setMaxResults(100)
                .getResultList().stream().map(RankYearResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeGyunggi(String term){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and useAreaType = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("searchYear", calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new).collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatTradeList_ByCity(String sidoCode, String term){
        return em.createQuery("select a from StatSidoYYMM a"
                        + " where a.sidoCode = :sidoCode and use_area_type = 'UA01'"
                        + " and a.dealYear >= :searchYear "
                        + " order by a.dealYYMM desc", StatSidoYYMM.class)
                .setParameter("sidoCode", sidoCode)
                .setParameter("searchYear", calcYearByTerm(term))
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

    public List<StatResponseDto> getStatTheme(String themeCode) {
        return em.createQuery("select a from StatTheme a "
                        + " where a.themeCode = :themeCode "
                        + " order by a.date desc ", StatTheme.class)
                .setParameter("themeCode", themeCode)
                .getResultList().stream().map(StatResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<StatResponseDto> getStatBuildYearList(String regnCode, String buildYear, String term) {
        return em.createQuery("select a from StatBuildYear a "
                        + " where a.regnCode = :regnCode "
                        + "   and a.buildYear = :buildYear "
                        + "   and a.dealYear >= :searchYear "
                        + " order by a.dealYear desc", StatBuildYear.class)
                .setParameter("regnCode", regnCode)
                .setParameter("buildYear", buildYear)
                .setParameter("searchYear", calcYearByTerm(term))
                .getResultList().stream().map(StatResponseDto::new)
                .collect(Collectors.toList());
    }

    private String calcYearByTerm(String term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int termInt = Integer.parseInt(term);
        int nowInt = Integer.parseInt(date.substring(0,4));
        // 현재날짜-term = 조회 기준일자
        return Integer.toString(nowInt - termInt);
    }
}

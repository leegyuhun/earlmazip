package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.AptLeaseResponseDto;
import aptdata.earlmazip.controller.dto.AptPriceResponseDto;
import aptdata.earlmazip.domain.AptLeaseRaw;
import aptdata.earlmazip.domain.AptPriceRaw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LeaseRepository {

    private final EntityManager em;

    /* 서울 전세 */
    public List<AptLeaseResponseDto> getLeaseList_SeoulSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '11' "
                        + "   and a.sigunguCode = :sigunguCode "
                        + "   and a.monthlyRent = 0 "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    /* 서울 갱신권사용 */
    public List<AptLeaseResponseDto> getLeaseRenewalList_SeoulSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '11' "
                        + "   and a.sigunguCode = :sigunguCode "
                        + "   and a.befMonthlyRent = 0 "
                        + "   and a.dealType = '갱신' "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    /* 서울 월세 */
    public List<AptLeaseResponseDto> getLeaseMonthlyList_SeoulSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '11' "
                        + "   and a.sigunguCode = :sigunguCode "
                        + "   and a.monthlyRent > 0 "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseList_GyunggiSido(String sidocode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '41' "
                        + "   and a.sidoCode = :sidoCode "
                        + "   and a.monthlyRent = 0 "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sidoCode", sidocode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseRenewalList_GyunggiSigungu(String sigungucode) {
        if (sigungucode.length() == 4) {
            return em.createQuery("select a from AptLeaseRaw a"
                            + " where a.dealYear = 2022 and a.areaCode = '41' "
                            + "   and a.sidoCode = :sigunguCode "
                            + "   and a.befMonthlyRent = 0 "
                            + "   and a.dealType = '갱신' "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("sidoCode", sigungucode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());

        } else {
            return em.createQuery("select a from AptLeaseRaw a"
                            + " where a.dealYear = 2022 and a.areaCode = '41' "
                            + "   and a.sigunguCode = :sigunguCode "
                            + "   and a.befMonthlyRent = 0 "
                            + "   and a.dealType = '갱신' "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("sigunguCode", sigungucode)
                    .setMaxResults(100)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        }

    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_GyunggiSido(String sidocode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '41' "
                        + "   and a.sidoCode = :sidoCode "
                        + "   and a.monthlyRent > 0 "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sidoCode", sidocode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseList_IncheonSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '28' "
                        + "   and a.sigunguCode = :sigunguCode"
                        + "   and a.monthlyRent = 0 "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseMonthlyList_IncheonSigungu(String sigungucode) {
        return em.createQuery("select a from AptLeaseRaw a"
                        + " where a.dealYear = 2022 and a.areaCode = '28' "
                        + "   and a.sigunguCode = :sigunguCode "
                        + "   and a.monthlyRent > 0 "
                        + " order by a.dealDate desc", AptLeaseRaw.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
    }

    public List<AptLeaseResponseDto> getLeaseList_ByName(String regnCode, String dong, String aptName, int ua, int term) {
        if (ua == 0) {
            return em.createQuery(" select a from AptLeaseRaw a "
                            + " where a.sigunguCode = :regncode "
                            + "   and a.landDong = :landDong"
                            + "   and a.dealYear >= :searchYear "
                            + "   and a.aptName = :aptname "
                            + "   and a.monthlyRent = 0 "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("regncode", regnCode)
                    .setParameter("landDong", dong)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .setParameter("aptname", aptName)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        } else {
            return em.createQuery(" select a from AptLeaseRaw a "
                            + " where a.sigunguCode = :regncode "
                            + "   and a.landDong = :landDong"
                            + "   and a.dealYear >= :searchYear "
                            + "   and a.aptName = :aptname "
                            + "   and a.monthlyRent = 0 "
                            + "   and a.useAreaTrunc = :ua "
                            + " order by a.dealDate desc", AptLeaseRaw.class)
                    .setParameter("regncode", regnCode)
                    .setParameter("landDong", dong)
                    .setParameter("searchYear", calcYearByTerm(term))
                    .setParameter("aptname", aptName)
                    .setParameter("ua", ua)
                    .getResultList().stream().map(AptLeaseResponseDto::new).collect(Collectors.toList());
        }
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

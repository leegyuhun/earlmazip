package aptdata.earlmazip.repository;

import aptdata.earlmazip.controller.dto.EcosDataResponseDto;
import aptdata.earlmazip.domain.EcosData;
import aptdata.earlmazip.domain.SiteInfo;
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

    public List<EcosDataResponseDto> getEcosData(String statCode, String itemCode, String term) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        // 현재날짜
        String date = simpleDateFormat.format(new Date());
        int termInt = Integer.parseInt(term);
        int nowInt = Integer.parseInt(date.substring(0,4));
        // 현재날짜-term = 조회 기준일자
        String searchYear = Integer.toString(nowInt - termInt);

        return em.createQuery(" select a from EcosData a "
                        + " where a.statCode = :statCode and itemCode1 = :itemCode "
                        + "   and a.year >= :searchYear "
                        + " order by a.date asc ", EcosData.class)
                .setParameter("statCode", statCode)
                .setParameter("itemCode", itemCode)
                .setParameter("searchYear", searchYear)
                .getResultList().stream().map(EcosDataResponseDto::new).collect(Collectors.toList());
    }
}

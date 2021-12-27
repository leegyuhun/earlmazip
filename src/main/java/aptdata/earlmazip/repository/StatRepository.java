package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.stat_area_yymm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatRepository {
    private final EntityManager em;

    public List<stat_area_yymm> findSeoul(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .getResultList();
    }

    public List<stat_area_yymm> findGyungGi(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '41' and use_area_type = 'UA01'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .getResultList();
    }
}

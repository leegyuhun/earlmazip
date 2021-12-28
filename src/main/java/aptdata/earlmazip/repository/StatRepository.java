package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.stat_area_yymm;
import aptdata.earlmazip.domain.stat_sido_yymm;
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

    public List<stat_area_yymm> findSeoulYear(String year){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + "   and a.dealYear = :dealYaer"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .setParameter("dealYaer", year)
                .getResultList();
    }

    public List<stat_area_yymm> findSeoulUA(String ua){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and a.useAreaType = :useAreaType"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .setParameter("useAreaType", ua)
                .getResultList();
    }

    public List<stat_area_yymm> findGyungGi(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '41' and use_area_type = 'UA01'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .getResultList();
    }

    public List<stat_area_yymm> findGyungGiYear(String year){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '41' and use_area_type = 'UA01'"
                        + "    and a.dealYear = :dealYear"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .setParameter("dealYear", year)
                .getResultList();
    }

    public List<stat_area_yymm> findGyungGiUA(String ua){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '41' and a.useAreaType = :useAreaType"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .setParameter("useAreaType", ua)
                .getResultList();
    }

    public List<stat_sido_yymm> findGyungGiSi(String sidoCode){
        return em.createQuery("select a from stat_sido_yymm a"
                        + " where a.sidoCode = :sidoCode and use_area_type = 'UA01'"
                        + " order by a.dealYYMM desc", stat_sido_yymm.class)
                .setParameter("sidoCode", sidoCode)
                .getResultList();
    }
}

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

    public List<stat_area_yymm> findSeoulYear(String year){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + "   and a.dealYear = :dealYaer"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .setParameter("dealYaer", year)
                .getResultList();
    }

    public List<stat_area_yymm> findSeoulUA2(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA02'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .getResultList();
    }

    public List<stat_area_yymm> findSeoulUA3(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA03'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .getResultList();
    }

    public List<stat_area_yymm> findSeoulUA4(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA04'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .getResultList();
    }

    public List<stat_area_yymm> findSeoulUA5(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA05'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
                .getResultList();
    }

    public List<stat_area_yymm> findSeoulUA6(){
        return em.createQuery("select a from stat_area_yymm a"
                        + " where a.areaCode = '11' and use_area_type = 'UA06'"
                        + " order by a.dealYYMM desc", stat_area_yymm.class)
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
                .setParameter("dealYaer", year)
                .getResultList();
    }
}

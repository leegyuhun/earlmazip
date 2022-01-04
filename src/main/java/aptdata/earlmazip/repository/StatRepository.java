package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.RankYear;
import aptdata.earlmazip.domain.StatAreaYYMM;
import aptdata.earlmazip.domain.StatSidoYYMM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatRepository {
    private final EntityManager em;

    public List<StatAreaYYMM> findSeoul(){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .getResultList();
    }

    public List<StatAreaYYMM> findSeoulYear(String year){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '11' and use_area_type = 'UA01'"
                        + "   and a.dealYear = :dealYaer"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("dealYaer", year)
                .getResultList();
    }

    public List<StatAreaYYMM> findSeoulUA(String ua){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '11' and a.useAreaType = :useAreaType"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("useAreaType", ua)
                .getResultList();
    }

    public List<RankYear> findSeoulTop(String sigungucode){
        return em.createQuery(" select a from RankYear a "
                            + " where a.gubnCode = :sigunguCode and a.dealYear = '2021'"
                            + " order by a.dealAmt desc", RankYear.class)
                .setParameter("sigunguCode", sigungucode)
                .setMaxResults(100)
                .getResultList();
    }

    public List<StatAreaYYMM> findGyungGi(){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and useAreaType = 'UA01'"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .getResultList();
    }

    public List<StatAreaYYMM> findGyungGiYear(String year){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and useAreaType = 'UA01'"
                        + "    and a.dealYear = :dealYear"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("dealYear", year)
                .getResultList();
    }

    public List<StatAreaYYMM> findGyungGiUA(String ua){
        return em.createQuery("select a from StatAreaYYMM a"
                        + " where a.areaCode = '41' and a.useAreaType = :useAreaType"
                        + " order by a.dealYYMM desc", StatAreaYYMM.class)
                .setParameter("useAreaType", ua)
                .getResultList();
    }

    public List<StatSidoYYMM> findGyungGiSi(String sidoCode){
        return em.createQuery("select a from StatSidoYYMM a"
                        + " where a.sidoCode = :sidoCode and use_area_type = 'UA01'"
                        + " order by a.dealYYMM desc", StatSidoYYMM.class)
                .setParameter("sidoCode", sidoCode)
                .getResultList();
    }

    public List<RankYear> findGyungGiTop(String sidocode){
        return em.createQuery(" select a from RankYear a "
                        + " where a.gubnCode = :sidoCode and a.dealYear = '2021'"
                        + " order by a.dealAmt desc", RankYear.class)
                .setParameter("sidoCode", sidocode)
                .setMaxResults(100)
                .getResultList();
    }

}

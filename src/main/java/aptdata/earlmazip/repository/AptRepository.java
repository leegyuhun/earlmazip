package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.AptInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AptRepository {

    private final EntityManager em;

    public List<AptInfo> findAllByString(AptSearch aptSearch) {
        String jpql = "select o from AptInfo o";
        boolean isFirstCondition = true;

        if (StringUtils.hasText(aptSearch.getAptName())) {
            if (isFirstCondition) {
                jpql += " where ";
                isFirstCondition = false;
            } else{
                jpql += " and";
            }
            jpql += " o.aptName like :aptName";
        }else{
            List<AptInfo> lists = new ArrayList<>();
            return lists;
        }
        TypedQuery<AptInfo> query = em.createQuery(jpql, AptInfo.class)
                .setMaxResults(50);
        if (StringUtils.hasText(aptSearch.getAptName())) {
            query = query.setParameter("aptName", '%' + aptSearch.getAptName() + '%');
        }

        return query.getResultList();
    }

    public List<AptInfo> findAll(){
        return em.createQuery("select a from AptInfo a where a.aptName = :aptName", AptInfo.class)
                .setParameter("aptName", "래미안안양메가트리아")
                .getResultList();
    }

    public List<AptInfo> findByName(String name){
        return em.createQuery("select a from AptInfo a where a.aptName = :aptName", AptInfo.class)
                .setParameter("aptName", name)
                .getResultList();
    }
}

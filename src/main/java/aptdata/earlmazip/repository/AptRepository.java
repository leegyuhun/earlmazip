package aptdata.earlmazip.repository;

import aptdata.earlmazip.domain.aptInfo;
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

    public List<aptInfo> findAllByString(AptSearch aptSearch) {
        String jpql = "select o from aptInfo o";
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
            List<aptInfo> lists = new ArrayList<>();
            return lists;
        }
        TypedQuery<aptInfo> query = em.createQuery(jpql, aptInfo.class)
                .setMaxResults(50);
        if (StringUtils.hasText(aptSearch.getAptName())) {
            query = query.setParameter("aptName", '%' + aptSearch.getAptName() + '%');
        }

        return query.getResultList();
    }

    public List<aptInfo> findAll(){
        return em.createQuery("select a from aptInfo a where a.aptName = :aptName", aptInfo.class)
                .setParameter("aptName", "래미안안양메가트리아")
                .getResultList();
    }

    public List<aptInfo> findByName(String name){
        return em.createQuery("select a from aptInfo a where a.aptName = :aptName", aptInfo.class)
                .setParameter("aptName", name)
                .getResultList();
    }
}
